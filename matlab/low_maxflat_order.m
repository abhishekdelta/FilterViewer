function [G]=low_maxflat_order(A,ws,wc,Ro);

%calculates g(k) required to construct low pass prototype filter with 
%maximally flat or butterworth response using insertion loss method
%generator and load impedances are equal to 1
%once values of g's are found any response can be computed using 
%frequency and impedance scaling
%the 3 dB frequency is at ww=(w/wc)=1

%Wc= 3 dB level
%Ws= frequency at which attenuation is specified
%A= Attenuation at Ws

%Calculate the value of n using the attenuation given at value of w=ws
n=ceil(0.5*(log((10)^(A/10)-1)/log(ws/wc)));
n

k=1:n;
g=2*sin((2*k-1)*pi/(2*n));
s=1;    %source impedance
r=1;    %load impedance

%plot of insertion loss of lowpass prototype filter 
ww=0:0.0001:10;
figure;
plot(ww,A),xlabel('(w/wc)- Normalized frequency'), ylabel('(A)- Insertion loss (dB)'),title('Maximally flat response');

%impedance and frequency scaling
Rs=Ro*s;
Rl=Ro*r;
j=1;
k=1;

for(i=1:n)
    if(rem(i,2)==1)
        G1(j)=(g(i)/(Ro*wc));
        j=j+1;
    else
        G2(k)=(g(i)*Ro/wc);
        k=k+1;
    end
end
G1
G2
if(rem(n,2)==0)
   G=[G1;G2];
else
    G=[G1;[G2 0]];
end

end