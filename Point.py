class Point:
    def __init__(self, x, y):
        self.x = x
        self.y = y
        self.z = 0

    def __str__(self):
        return "x:%.1f, y:%.1f, z:%.2f" % (self.x, self.y, self.z)

    def __add__(self, other):
        return Point(self.x + other.x, self.y + other.y)

    def __sub__(self, other):
        return Point(self.x - other.x, self.y - other.y)

    def __mul__(self, scalar):
        return Point(self.x * scalar, self.y * scalar)

    def __div__(self, scalar):
        x = self.x / scalar
        y = self.y / scalar
        return Point(x, y)

    def copy(self, other):
        other.x = self.x
        other.y = self.y
        other.z = self.z

    def evaluate_function(self, func):
        self.z = func(self.x, self.y)