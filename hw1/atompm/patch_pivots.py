"""Fix AToMPM's Pivots copy methods for Python 3.12+.

Pivots.__copy__/__deepcopy__ copy super(Pivots, self), a proxy object rather
than a dict, so every rule match raises RecursionError (deepcopy) or
AttributeError (copy) on newer Pythons.
"""
import re
import sys

path = sys.argv[1] if len(sys.argv) > 1 else "/opt/atompm/mt/ptcal/pytcore/tcore/messages.py"
src = open(path).read()

old = re.compile(
    r"    def __copy__\(self\):\n"
    r"        cpy = copy\.copy\(super\(Pivots, self\)\)\n"
    r"        cpy\.has_source_node_indices = self\.has_source_node_indices\n"
    r"        return cpy\n"
    r"\n"
    r"    def __deepcopy__\(self, memo\):\n"
    r"        cpy = copy\.deepcopy\(super\(Pivots, self\)\)\n"
    r"        cpy\.has_source_node_indices = self\.has_source_node_indices\n"
    r"        return cpy\n")

new = '''    def __copy__(self):
        cpy = Pivots()
        cpy.update(self)
        cpy.has_source_node_indices = self.has_source_node_indices
        return cpy

    def __deepcopy__(self, memo):
        cpy = Pivots()
        cpy.update(copy.deepcopy(dict(self), memo))
        cpy.has_source_node_indices = self.has_source_node_indices
        return cpy
'''

if "cpy = Pivots()" in src:
    print("already patched")
else:
    patched, n = old.subn(new, src)
    if n != 1:
        sys.exit("patch_pivots: expected Pivots copy methods not found in " + path)
    open(path, "w").write(patched)
    print("patched", path)
