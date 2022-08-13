from util import *

def glyph(character):
  return {
    "type": "minecraft:stonecutting",
    "count": 1,
    "ingredient": {
      "tag": "ochetgenyo:glyph_blocks"
    },
    "result": f"ochetgenyo:{character}_glyph"
  }

def item_criterion(items):
  return {
    "trigger": "minecraft:inventory_changed",
    "conditions": {
      "items": [
        {
          "items": items
        }
      ]
    }
  }

def advancement():
  return {
    "parent": "minecraft:recipes/root",
    "rewards": {
      "recipes": ID_BLOCK_LIST
    },
    "criteria": {
      "has_basalt": item_criterion(["minecraft:basalt"]),
      "has_blackstone": item_criterion(["minecraft:blackstone", "minecraft:polished_blackstone", "minecraft:blastone_bricks"]),
      "has_the_recipe": {
        "trigger": "minecraft:recipe_unlocked",
        "conditions": {
          "recipe": "ochetgenyo:glyph_base"
        }
      }
    },
    "requirements": [
      [
        "has_basalt",
        "has_blackstone",
        "has_the_recipe"
      ]
    ]
  }