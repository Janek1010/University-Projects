function [xvect,xdif,fx,it_cnt] = secant(fun,a,b,eps)
% fun - funkcja, ktorej miejsce zerowe bedzie poszukiwane
% [a,b] - przedzial poszukiwania miejsca zerowego
% eps - prog dokladnosci obliczen
% 
% xvect - wektor kolejnych wartosci przyblizonego rozwiazania
% xdif - wektor roznic pomiedzy kolejnymi wartosciami przyblizonego rozwiazania
% fx - wektor wartosci funkcji dla kolejnych elementow wektora xvect
% it_cnt - liczba iteracji wykonanych przy poszukiwaniu miejsca zerowego

x_kminus1 = a;
x_k = b;

xvect = [];
xdif = [];
fx = [];

for i = 1:3000
    x_kplus1 = x_k - ((fun(x_k) * (x_k -x_kminus1)) / (fun(x_k) - fun(x_kminus1)));
    
    xvect(i) = x_kplus1;
    xdif(i) = abs(x_kplus1-x_k);
    fx(i) = fun(x_kplus1);
    
    x_kminus1 = x_k;
    x_k = x_kplus1;
    
    if abs(fx(i)) < eps
        it_cnt = i;
        return;
    end
end


end

