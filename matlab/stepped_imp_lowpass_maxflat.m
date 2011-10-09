function [W,L,Z,n]= stepped_imp_lowpass_maxflat(Wc,Ws,A,Zo,Zl,Zh,Er,b,type)

%[W,L,Z]=stepped_imp_lowpass_maxflat(Wc,Ws,A,Zo,Zl,Zh,Er,b,type);
%calculates stepped impedance lowpass filter
%W,L,Z= width, length and characteristic impedance of stripline of various sections
%Wc= cutoff frequency
%Ws= frequency at which attenuation is specified
%A= Attenuation at Ws
%Zo= characteristic impedance of stripline connected to load and generator
%Zl,Zh= characteristic impedance of stripline for inductor and capacitor
%Er= dielectric constant
%b= substrate width
%type= 1-'stripline' or 2-'microstripline'


clc;
[n]=order_maxflat(A,Wc,Ws);
if(n<=0)
    error('One or more of your inputs is invalid');
end

n=n+1;
n
[s,g,r]=max_flat(n);

for(i=1:2:n)
    Bl(i)=asin(g(i)*Zl/Zo);
end

for(i=2:2:n)
    Bl(i)=asin(g(i)*Zo/Zh);
end

W=[];Z=[];L=[];

if(type==1)    %implies stripline design
    
    %calculating impedances
    for(j=1:n)
        if(rem(j,2)==1)
            Z(j)=Zl;
        else
            Z(j)=Zh;
        end
    end
    
    %calculating effective width and length
    for(j=1:n)
        [W(j),L(j)]=stripline(Z(j),Er,b,Bl(j),Wc);
    end
    [Wg,Lg]=stripline(Zo,Er,b,pi/2,Wc);
    Zg=Zo;
    Zr=Zo;
    Wr=Wg;
    Lr=Lg;
        
elseif(type==2)        %implies microstrip design

    %calculating impedances
    for(j=1:n)
        if(rem(j,2)==1)
            Z(j)=Zl;
        else
            Z(j)=Zh;
        end
    end
    
    %calculating effective width and length
    for(j=1:n)
        [W(j),L(j)]=microstripline(Z(j),Er,b,Bl(j),Wc);
    end
    [Wg,Lg]=microstripline(Zo,Er,b,pi/2,Wc);
    Zg=Zo;
    Zr=Zo;
    Wr=Wg;
    Lr=Lg;
end

W=[Wg W Wr];
L=[Lg L Lr];
Z=[Zg Z Zr];

figure;
[X,Y,Z]=plot_stepped_imp_lpf(W,L);

end  