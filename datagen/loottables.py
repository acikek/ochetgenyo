from util import *

def loot_table(block):
  return {
    "type": "minecraft:block",
    "pools": [
      {
        "rolls": 1,
        "entries": [
          {
            "type": "minecraft:item",
            "name": block
          }
        ],
        "conditions": [
          {
            "condition": "minecraft:survives_explosion"
          }
        ]
      }
    ]
  }