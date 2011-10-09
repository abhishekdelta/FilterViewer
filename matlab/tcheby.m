function [g]=tcheby(n,Am)

% Calculation of g values for low pass prototype filter with equiripple passband and flat stopband
% Am corresponds to ripple level is at w=(w/wc)=1

k=1:n;
a=sin((2*k-1)*pi/(2*n));
B=log(coth(Am/17.37));
v=sinh(B/(2*n));

for(i=1:n)
    b(i)=(v^2)+(sin(i*pi/n))^2;
end

g(1)=2*a(1)/v;

for(i=2:n)
    g(i)=(4*a(i-1)*a(i))/(b(i-1)*g(i-1));
end
b
g