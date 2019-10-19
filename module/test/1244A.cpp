#include<iostream>
#include<cmath>
using namespace std;
int main() {
	int testCases;
	scanf("%d", &testCases);
	while (testCases--) {
		double a, b, x, y, k;
		scanf("%lf%lf%lf%lf%lf", &a, &b, &x, &y, &k);

		double A = ceil(a / x);
		double B = ceil(b / y);
		if (A + B > k) {
			printf("-1\n");
		}
		else {
			printf("%d %d\n", (int)A, (int)B);
		}
	}
	return 0;
}