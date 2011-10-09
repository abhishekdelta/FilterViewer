function [s,g,r]=Tchebycheff(n,Am)

%[s,g,r]=Tchebycheff(n,Am);
%calculates g's required to construct low pass prototype filter with 
%equiripple passband and flat stopband or Tchebycheff response using 
%insertion loss method
%once values of g's are found any response can be computed using 
%frequency and impedance scaling
%Am corresponds to ripple level is at ww=(w/wc)=1

%calculation of g
for k=1:1:n
a(k)=sin((2*k-1)*pi/(2*n));
end
B=log(coth(Am/17.37));
v=sinh(B/(2*n));
for(i=1:n)
    b(i)=(v^2)+(sin(i*pi/n))^2;
end

g(1)=2*a(1)/v;

for(i=2:n)
    g(i)=(4*a(i-1)*a(i))/(b(i-1)*g(i-1));
end

%calculation of load impedance
if(rem(n,2)==0)
    r=(coth(B/4))^2;
else
    r=1;
end

s=1;    %source impedance

%plot of insertion loss of lowpass prototype filter
ww1=0:0.001:1;
ww2=1.001:0.001:3;
ww=[ww1 ww2];
A1=10.*log10(1+(10^(Am/10)-1).*cos(n.*acos(ww1)));
A2=10.*log10(1+(10^(Am/10)-1).*cosh(n.*acosh(ww2)));
A=[A1 A2];
figure, plot(ww,A),xlabel('(w/wc)- Normalized frequency'), ylabel('(A)- Insertion loss (dB)'),title('Tchebycheff response');
end
