import json
import os

from collections import namedtuple
from shutil import copyfile

from util import *
import blockstates
import models

Block = namedtuple("Block", ["name", "blockstate", "models"])

def write_file(path, obj):
  os.makedirs(os.path.dirname(path), exist_ok=True)
  with open(f"{path}.json", "w") as file:
    file.write(f"{json.dumps(obj, indent=2)}\n")
    file.close()

def write(block):
  write_file(f"generated/blockstates/{block.name}", { "variants": block.blockstate })
  for path, model in block.models.items():
    write_file(f"generated/models/block/{path}", model)

def base():
  return Block("glyph_base", blockstates.base("base"), models.base("base"))

def glyphs(list, blockstate, model):
  return [Block(
    f"{g}_glyph", 
    blockstate(g), 
    model(g)
  ) for g in list]

def stop():
  return Block("stop_glyph", blockstates.base("glyph/stop"), models.base("glyph/stop", True))

write(base())
write(stop())
for c in glyphs(CONSONANTS, blockstates.consonant, models.consonant):
  write(c)
for v in glyphs(VOWELS, blockstates.vowel, models.vowel):
  write(v)

for template, dst in MODEL_TEMPLATES.items():
  path = f"generated/models/block/{dst}.json"
  os.makedirs(os.path.dirname(path), exist_ok=True)
  copyfile(f"templates/{template}.json", path)