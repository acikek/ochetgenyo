VOWELS = ["a", "e", "o"]
CONSONANTS = ["t", "k", "j", "v", "s", "n", "p", "g"]

BLOCK = {
  "orientation": ["a"],
  "double": ["a", "t"]
}

CONNECTION_TYPES = ["none", "top", "bottom", "both"]

CONNECTIONS = {
  "none": CONNECTION_TYPES,
  "top": ["top", "both"],
  "bottom": ["bottom", "both"],
  "both": ["both"]
}

FACING = { 
  "east": 90,
  "north": 0, 
  "south": 180, 
  "west": 270 
}

STATIC_CONTENT = {
  "base": "assets/models/block/base/template", 
  "glyph": "assets/models/block/glyph/template", 
  "glyph2": "assets/models/block/glyph/template2"
}

TEXTURE_OVERRIDES = {
  "o_right_2": "o_normal_2",
  "o_left_2": "o_normal_2",
  "k_top_2": "k_none_2",
  "k_both_2": "k_bottom_2",
  "n_bottom_2": "n_none_2",
  "stop": "stop/layer_1",
  "stop_2": "stop/layer_2"
}

ITEM_MODEL = {
  "a": "normal",
  "e": "right",
  "o": "right"
}

def iter_forms(all, fn):
  for form, connections in CONNECTIONS.items():
    for connection in CONNECTION_TYPES if all else connections:
      fn(form, connection)

def vowel_path(orientation, attached):
  objs = []
  if orientation is not None:
    objs.append(orientation)
  if attached:
    objs.append("attached")
  if len(objs) == 0:
    return "normal"
  return "_".join(objs)

def iter_vowel_forms(character, fn):
  for connection in CONNECTIONS:
    for attached in [True, False]:
      for orientation in ["left", "right"] if character not in BLOCK["orientation"] else [None]:
        fn(connection, attached, orientation, vowel_path(orientation, attached))

def identifier(path):
  return f"ochetgenyo:block/{path}"