from util import *

BASE_TEMPLATE = identifier("base/template")
GLYPH_TEMPLATE = identifier("glyph/template")

def make_model(template, obj):
  return {
    "parent": template,
    "textures": obj
  }

def add_model_entry(models, path, template, obj):
  models[path] = make_model(template, obj)

def base():
  models = {}
  for connection in CONNECTIONS:
    add_model_entry(models, f"base/{connection}", BASE_TEMPLATE, {
      "front": identifier(f"base/{connection}")
    })
  return models

def glyph_obj(connection, path):
  return {
    "front": identifier(f"base/{connection}"),
    "overlay": identifier(f"glyph/{path}")
  }

def consonant(character):
  models = {}
  iter_forms(False, lambda form, connection: add_model_entry(models, f"glyph/consonant/{character}/{form}/{connection}", GLYPH_TEMPLATE, 
    glyph_obj(connection, f"consonant/{character}_{form}")))
  return models

def vowel(character):
  models = {}
  iter_vowel_forms(lambda connection, attached, orientation: add_model_entry(models, 
    f"glyph/vowel/{character}/{connection}/{orientation}{'_attached' if attached else ''}", GLYPH_TEMPLATE, 
    glyph_obj(connection, f"vowel/{character}_{orientation}{'_attached' if attached else ''}")))
  return models