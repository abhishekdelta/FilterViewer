function[] = interdigital_maxflat(w0,ws,Am,fbw,Er,t,b)

%design of n-reactive element prototype
%w0 cut off frequency
%ws frequency at which attenuation is given
%Am attenuation at ws
%fbw fractional bandwidth
%Er=dielectric constant of the medium
%b= substrate width


wpwc=1/fbw*(ws/w0-w0/ws);
w1=w0-(fbw*w0);
w2=w0+(fbw*w0);

%calculate order n
n=ceil(0.5*(log((10)^(Am/10)-1)/log(wpwc)));
if(n<=2)
    error('One or more of your inputs is invalid');
end
n


%calculate values of g
for k=1:n
    g(k)= 2*sin((2*k-1)*pi/(2*n));
end
g(n+1)=1;


%Display the frequency response of the band pass filter
z=0:0.01:3;
for k=length(z):-1:1
A(k)=10*log10(1+z(k)^10);
end

figure(1);
plot(z*(w1-w0)+w0,A);

for k=1:length(z)
A1(k)=10*log10(1+z(k)^10);
end
hold on
plot(z*(w2-w0)+w0,A1);
hold off

% Stripline design
stripline_interdigital(w0,n,Er,g,t,b);
