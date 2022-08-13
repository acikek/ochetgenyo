import json
import os

from collections import namedtuple
from shutil import copyfile

from util import *
import blockstates
import models
import recipes

Block = namedtuple("Block", ["name", "blockstate", "models", "parent", "recipe"])

def write_file(path, obj):
  os.makedirs(os.path.dirname(path), exist_ok=True)
  with open(f"{path}.json", "w") as file:
    file.write(f"{json.dumps(obj, indent=2)}\n")
    file.close()

def write(block):
  write_file(f"generated/assets/blockstates/{block.name}", { "variants": block.blockstate })
  for path, model in block.models.items():
    write_file(f"generated/assets/models/block/{path}", model)
  write_file(f"generated/assets/models/item/{block.name}", {
    "parent": f"ochetgenyo:block/{block.parent}"
  })
  if block.recipe is not None:
    write_file(f"generated/data/recipes/{block.name}", block.recipe)

def base():
  return Block("glyph_base", blockstates.base("base"), models.base("base"), "base/none", None)

def glyphs(list, blockstate, model, kind):
  return [Block(
    f"{g}_glyph", 
    blockstate(g), 
    model(g),
    f"glyph/{kind}/{g}/none/{'none' if g not in ITEM_MODEL else ITEM_MODEL[g]}",
    recipes.glyph(g)
  ) for g in list]

def stop():
  return Block("stop_glyph", blockstates.base("glyph/stop"), models.base("glyph/stop", True), "glyph/stop/none", recipes.glyph("stop"))

write(base())
write(stop())
for c in glyphs(CONSONANTS, blockstates.consonant, models.consonant, "consonant"):
  write(c)
for v in glyphs(VOWELS, blockstates.vowel, models.vowel, "vowel"):
  write(v)

write_file("generated/data/advancements/recipes/glyph_base", recipes.advancement())

for file, dst in STATIC_CONTENT.items():
  path = f"generated/{dst}.json"
  os.makedirs(os.path.dirname(path), exist_ok=True)
  copyfile(f"static/{file}.json", path)