from util import *

BASE_TEMPLATE = identifier("base/template")
GLYPH_TEMPLATE = identifier("glyph/template")
GLYPH_TEMPLATE_2 = identifier("glyph/template2")

def make_model(template, obj):
  return {
    "parent": template,
    "textures": obj
  }

def add_model_entry(models, path, template, obj):
  models[path] = make_model(template, obj)

def fix_overlay(overlay):
  components = overlay.split("/")
  if components[-1] in TEXTURE_OVERRIDES:
    components[-1] = TEXTURE_OVERRIDES[components[-1]]
  return "/".join(components)

def add_overlay(obj, character, path):
  overlay = identifier(f"{path}")
  overlay2 = f"{overlay}_2"
  obj["overlay"] = fix_overlay(overlay)
  if character not in BLOCK["double"]:
    obj["overlay2"] = fix_overlay(overlay2)

def base(path, stop=False):
  models = {}
  for connection in CONNECTIONS:
    obj = {
      "front": identifier(f"base/{connection}")
    }
    if stop:
      add_overlay(obj, None, path)
    add_model_entry(models, f"{path}/{connection}", BASE_TEMPLATE if not stop else GLYPH_TEMPLATE_2, obj)
  return models

def glyph_obj(character, connection, path):
  obj = {
    "front": identifier(f"base/{connection}")
  }
  add_overlay(obj, character, f"glyph/{path}")
  return obj

def glyph_template(character):
  return GLYPH_TEMPLATE_2 if character not in BLOCK["double"] else GLYPH_TEMPLATE

def consonant(character):
  models = {}
  iter_forms(False, lambda form, connection: add_model_entry(models, f"glyph/consonant/{character}/{form}/{connection}", 
    glyph_template(character), glyph_obj(character, connection, f"consonant/{character}_{form}")))
  return models

def vowel(character):
  models = {}
  iter_vowel_forms(character, lambda connection, attached, orientation, path: add_model_entry(models, 
    f"glyph/vowel/{character}/{connection}/{path}", glyph_template(character), 
    glyph_obj(character, connection, f"vowel/{character}_{path}")))
  return models