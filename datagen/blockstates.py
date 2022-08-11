from util import *

def add_blockstate_entry(obj, path, facing, rotation, connection, extra=None, model=None):
  name = f"facing={facing},connection={connection}"
  if extra is not None:
    name += f",{extra}"
  append = "/".join(model) if model is not None else ""
  entry = {
    "model": identifier(f"{path}{'/' if model is not None else ''}{append}")
  }
  if rotation != 0:
    entry["y"] = rotation
  obj[name] = entry
  return obj

def base():
  obj = {}
  for facing, rotation in FACING.items():
    for connection in CONNECTIONS:
      add_blockstate_entry(obj, "base", facing, rotation, connection, model=[connection])
  return obj

def consonant(character):
  obj = {}
  for facing, rotation in FACING.items():
    iter_forms(True, lambda form, connection: add_blockstate_entry(
      obj, f"glyph/consonant/{character}/{form}", facing, rotation, connection, f"form={form}",
      ["both" if not connection in CONNECTIONS[form] else connection]
    ))
  return obj

def vowel(character):
  obj = {}
  for facing, rotation in FACING.items():
    iter_vowel_forms(character, lambda connection, attached, orientation, path: add_blockstate_entry(
      obj, f"glyph/vowel/{character}/{connection}/{path}", facing, rotation, connection,
      f"{f'orientation={orientation},' if orientation is not None else ''}attached={'true' if attached else 'false'}"
    ))
  return obj