function []=interdigital(n,Am,w,f,Er)

%n reactive element Chebychev prototype
%Am attenuation
%w fractional bandwidth
%f cut off frequency

%calculate values of g
[g]=tcheby(n,Am);

if (rem(n,2)==0)
    k=n/2;
else
    k=(n+1)/2;
end




end