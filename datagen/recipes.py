from util import CONSONANTS, VOWELS


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
  glyphs = CONSONANTS.copy()
  glyphs.extend(VOWELS)
  glyphs = list(map(lambda g: f"{g}_glyph", glyphs))
  glyphs.append("glyph_base")
  return {
    "parent": "minecraft:recipes/root",
    "rewards": {
      "recipes": list(map(lambda item: f"ochetgenyo:{item}", glyphs))
    },
    "criteria": {
      "has_basalt": item_criterion(["minecraft:basalt"]),
      "has_deepslate": item_criterion(["minecraft:deepslate", "minecraft:cobbled_deepslate"]),
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
        "has_deepslate",
        "has_the_recipe"
      ]
    ]
  }