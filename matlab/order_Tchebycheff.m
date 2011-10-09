function [n]=order_Tchebycheff(Am,Wm,Ws,A)

%calculates the order n of the Tchebycheff filter to be designed
%Am= ripple level at ww=(W/Wm)
%Wm= frequency at which ripple level is Am
%Ws= frequency in stopband at which attenuation A, is specified

%calculation of filter order n
n=ceil(acosh(sqrt(((10)^(A/10)-1)/((10)^(Am/10)-1)))/acosh(Ws/Wm));

end