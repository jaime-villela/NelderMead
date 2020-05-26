import numpy as np
from matplotlib.patches import Polygon
from Point import Point

class SimplexTriangle2Dim:
    def __init__(self, x1, y1, x2, y2, x3, y3, func):
        self.best = Point(x1, y1)
        self.good = Point(x2, y2)
        self.worst = Point(x3, y3)
        self.func = func
        self.mid_b_to_g = None
        self.mid_b_to_w = None
        self.reflection = None
        self.expansion = None
        self.contraction = None

    def __repr__(self):
        return "BEST "+repr(self.best)+"\nGOOD "+repr(self.good)+"\nWORST "+repr(self.worst)

    def eval_func_at_vertices(self):
        self.best.evaluate_function(self.func)
        self.good.evaluate_function(self.func)
        self.worst.evaluate_function(self.func)

    def eval_func_at_reflection(self):
        self.reflection.evaluate_function(self.func)

    def sort_vertices(self):
        sorted_vertices = sorted((self.best, self.good, self.worst), key=lambda o: o.z)
        self.best = sorted_vertices[0]
        self.good = sorted_vertices[1]
        self.worst = sorted_vertices[2]

    def did_vertices_converge(self):
        return (abs(self.best.z - self.good.z) < 0.001) and (abs(self.good.z - self.worst.z) < 0.001)

    def calc_mid_best_to_good(self):
        self.mid_b_to_g = (self.best + self.good) / 2

    def calc_mid_best_to_worst(self):
        self.mid_b_to_w = (self.best + self.worst) / 2

    def get_good_to_worst_midpoint(self):
        mid_g_to_w = (self.good + self.worst) / 2
        return mid_g_to_w.x, mid_g_to_w.y

    def calc_reflection_point(self):
        self.calc_mid_best_to_good()
        self.reflection = (2 * self.mid_b_to_g) - self.worst

    def calc_expansion_point(self):
        self.calc_mid_best_to_good()
        self.calc_reflection_point()
        self.expansion = (2 * self.reflection) - self.mid_b_to_g

    def is_reflection_lt_good(self):
        return self.reflection.z < self.good.z

    def calc_contraction_point(self):
        self.calc_mid_best_to_good()
        self.contraction = (self.worst + self.mid_b_to_g) / 2

    def expand(self):
        print("Compute E and f(E)")
        self.calc_expansion_point()
        self.expansion.evaluate_function(self.func)
        if self.expansion.z < self.best.z:
            print("Replace W with E")
            self.worst = self.expansion
        else:
            print("Replace W with R")
            self.worst = self.reflection

    def reflect_or_expand(self):
        if self.best.z < self.reflection.z:
            print("reflection")
            print("replace W with R")
            self.worst = self.reflection
        else:
            print("expansion")
            self.expand()

    def shrink(self):
        print("Compute S")
        self.calc_mid_best_to_worst()
        print("Replace W with S")
        self.worst = self.mid_b_to_w
        print("Replace G with M")
        self.good = self.mid_b_to_g

    def contract_or_shrink(self):
        print("contract")
        if self.reflection.z < self.worst.z:
            print("Replace W with R")
            self.worst = self.reflection

        self.calc_contraction_point()
        self.contraction.evaluate_function(self.func)

        if self.contraction.z < self.worst.z:
            print("replace W with C")
            self.worst = self.contraction
        else:
            self.shrink()

    def get_best_x_y(self):
        return self.best.x, self.best.y

    def get_vertices(self):
        return np.array([[self.best.x, self.best.y], [self.good.x, self.good.y], [self.worst.x, self.worst.y]])

