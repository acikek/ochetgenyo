VOWELS = ["a", "e", "o"]
CONSONANTS = ["t", "k", "j", "v", "s", "n", "p", "g"]

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

def iter_forms(all, fn):
  for form, connections in CONNECTIONS.items():
    for connection in CONNECTION_TYPES if all else connections:
      fn(form, connection)

def iter_vowel_forms(fn):
  for connection in CONNECTIONS:
    for attached in [True, False]:
      for orientation in ["left", "right"]:
        fn(connection, attached, orientation)

def identifier(path):
  return f"ochetgenyo:block/{path}"