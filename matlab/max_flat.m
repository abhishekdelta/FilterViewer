function [s,g,r]=max_flat(n);
%[s,g,r]=max_flat(n);
%calculates g(k) required to construct low pass prototype filter with 
%maximally flat or butterworth response using insertion loss method
%generator and load impedances are equal to 1
%once values of g's are found any response can be computed using frequency and impedance scaling
%the 3 dB frequency is at ww=(w/wc)=1

%calculation of g
k=1:n;
g=2*sin((2*k-1)*pi/(2*n));
s=1;    %source impedance
r=1;    %load impedance

%plot of insertion loss of lowpass prototype filter 
ww=0:0.001:5;
A=10.*log10(1+ww.^2);
figure, plot(ww,A),xlabel('(w/wc)- Normalized frequency'), ylabel('(A)- Insertion loss (dB)'),title('Maximally flat response');

end