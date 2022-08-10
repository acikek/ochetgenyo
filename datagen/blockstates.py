from util import *

def add_blockstate_entry(obj, path, facing, rotation, connection, extra=None, override=None):
  name = f"facing={facing},connection={connection}"
  if extra is not None:
    name += f",{extra}"
  entry = {
    "model": identifier(f"{path}{f'/{connection}' if override is None else override}")
  }
  if rotation != 0:
    entry["y"] = rotation
  obj[name] = entry
  return obj

def base():
  obj = {}
  for facing, rotation in FACING.items():
    for connection in CONNECTIONS:
      add_blockstate_entry(obj, "base", facing, rotation, connection)
  return obj

def consonant(character):
  obj = {}
  for facing, rotation in FACING.items():
    iter_forms(True, lambda form, connection: add_blockstate_entry(
      obj, f"glyph/consonant/{character}/{form}", facing, rotation, connection, f"form={form}",
      "/both" if not connection in CONNECTIONS[form] else f"/{connection}"
    ))
  return obj

def vowel(character):
  obj = {}
  for facing, rotation in FACING.items():
    iter_vowel_forms(lambda connection, attached, orientation: add_blockstate_entry(
      obj, f"glyph/vowel/{character}/{connection}/{orientation}{'_attached' if attached else ''}", facing, rotation, connection,
      f"orientation={orientation},attached={'true' if attached else 'false'}", ""
    ))
  return obj