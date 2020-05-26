class Point:
    def __init__(self, x, y, z=0):
        self.x = x
        self.y = y
        self.z = z

    def __repr__(self):
        return "x:%.1f, y:%.1f, z:%.2f" % (self.x, self.y, self.z)

    def __add__(self, other):
        return Point(self.x + other.x, self.y + other.y)

    def __sub__(self, other):
        return Point(self.x - other.x, self.y - other.y)

    def __mul__(self, scalar):
        return Point(self.x * scalar, self.y * scalar)

    def __rmul__(self, other):
        return Point(self.x * other, self.y * other)

    def __div__(self, scalar):
        return Point(self.x / scalar, self.y / scalar)

    def copy(self):
        return Point(self.x, self.y, self.z)

    def evaluate_function(self, func):
        self.z = func(self.x, self.y)