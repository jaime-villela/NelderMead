from Triangle import SimplexTriangle2Dim

class DownhillSimplex2Dim():
    def __init__(self, x1, y1, x2, y2, x3, y3, func):
        self.triangle = SimplexTriangle2Dim(x1, y1, x2, y2, x3, y3, func)
        self.num_iterations = 0

    def find_local_min(self):
        while True:
            self.num_iterations += 1
            print("iteration ", self.num_iterations)

            self.triangle.eval_func_at_vertices()
            self.triangle.sort_vertices()
            print(self.triangle)

            if self.triangle.did_vertices_converge():
                break

            self.triangle.calc_mid_best_to_good()
            self.triangle.calc_reflection_point()
            self.triangle.eval_func_at_reflection()

            if self.triangle.is_reflection_lt_good():
                self.triangle.reflect_or_expand()
            else:
                self.triangle.contract_or_shrink()

        return (self.triangle.best.x, self.triangle.best.y)