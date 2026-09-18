"""
make_glow_map.py — generates a LabPBR specular ("_s") map from an item texture,
where lighter/brighter pixels in the source texture glow more under Iris + a
PBR-capable shader pack (Complementary Unbound, etc).

Usage:
    python make_glow_map.py sprout.png
    python make_glow_map.py sprout.png --threshold 160 --max-glow 220

This writes the output right next to the input file, same name + "_s"
(e.g. sprout.png -> sprout_s.png), which is exactly where Iris looks for it.

Requires: pip install pillow
"""

import argparse
from pathlib import Path

from PIL import Image


def make_glow_map(src_path: Path, threshold: int, max_glow: int) -> Path:
    if not (0 <= threshold <= 255):
        raise ValueError("--threshold must be between 0 and 255")
    if not (0 <= max_glow <= 254):
        raise ValueError("--max-glow must be between 0 and 254 (LabPBR reserves 255)")

    img = Image.open(src_path).convert("RGBA")
    width, height = img.size
    out = Image.new("RGBA", (width, height))

    src_pixels = img.load()
    out_pixels = out.load()

    for y in range(height):
        for x in range(width):
            r, g, b, a = src_pixels[x, y]

            if a == 0:
                # Transparent in the base texture -> transparent here too, no glow.
                out_pixels[x, y] = (0, 0, 0, 0)
                continue

            brightness = (r + g + b) / 3

            if brightness >= threshold:
                # Scale brightness above the threshold into a 0..max_glow range.
                span = 255 - threshold
                strength = int((brightness - threshold) / span * max_glow) if span > 0 else max_glow
                strength = max(0, min(strength, max_glow))
            else:
                strength = 0

            # RGB = 0 (non-reflective, no smoothness/metalness/porosity data).
            # Alpha = glow strength, the only channel that matters for pure emission.
            out_pixels[x, y] = (0, 0, 0, strength)

    out_path = src_path.with_name(src_path.stem + "_s" + src_path.suffix)
    out.save(out_path)
    return out_path


def main():
    parser = argparse.ArgumentParser(description="Generate a LabPBR glow (specular/emissive) map from an item texture.")
    parser.add_argument("source", type=Path, help="Path to the base texture, e.g. sprout.png")
    parser.add_argument("--threshold", type=int, default=180,
                         help="Brightness (0-255) below which pixels get NO glow. Default: 180")
    parser.add_argument("--max-glow", type=int, default=200,
                         help="Strongest glow value (0-254, never 255) for the brightest pixels. Default: 200")
    args = parser.parse_args()

    if not args.source.exists():
        raise SystemExit(f"File not found: {args.source}")

    out_path = make_glow_map(args.source, args.threshold, args.max_glow)
    print(f"Saved {out_path}")


if __name__ == "__main__":
    main()