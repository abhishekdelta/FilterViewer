function[] = stripline_interdigital(w0,n,Er,g,t,b)

% Stripline design of the interdigital bandpass filter
%h is a dimensionless admittance scale factor to be specified to give a
%convenient admittance level in the filter
%Ya is the characteristic admittance (1/50 mho)
%u=w1/w0 is to be defined


Ya=0.02;
u=0.1;
lambda=3*(10^8)/w0/sqrt(Er);
l=lambda/4;
display(l);
theta1=pi/2*(1-u/2);

JpY(1,2)=1/sqrt(g(1)*1);
for k=1:n-1
    JpY(k+1,k+2)=1/((1)*sqrt(g(k)*g(k+1)));
end
JpY(n+1,n+2)=1/sqrt(g(n)*g(n+1)*(1));

for k=1:n-1
N(k,k+1)=sqrt((JpY(k+1,k+2))^2+(tan(theta1)/2)^2);
end

%calculation of h

if(mod(n,2))
   k=(n+1)/2;
else
   k=n/2; 
end

h=(5.4)/(2*(376.7*Ya/sqrt(Er)*JpY(k,k+1))+(376.7*Ya/sqrt(Er)*(N(k-1,k)+N(k,k+1)-JpY(k,k+1)-JpY(k+1,k+2)))+2*(376.7*Ya/sqrt(Er)*JpY(k+1,k+2)));

M1=Ya*(JpY(1,2)*sqrt(h) + 1);
Mn=Ya*(JpY(n+1,n+2)*sqrt(h) + 1);

%the normalised self capacitances Ck/e per unit length for the line elements are - 

CpE(1,1)=376.7/sqrt(Er)*(2*Ya-M1);
CpE(2,2)=376.7/sqrt(Er)*(Ya-M1+h*Ya*(tan(theta1)/2 + (JpY(1,2))^2 + N(1,2) - JpY(2,3)));

for k=2:n-1
CpE(k+1,k+1)=376.7/sqrt(Er)*h*Ya*(N(k-1,k)+N(k,k+1)-JpY(k,k+1)-JpY(k+1,k+2));
end

CpE(n+1,n+1)=376.7/sqrt(Er)*(Ya-Mn+h*Ya*(tan(theta1)/2 + (JpY(n+1,n+2))^2 + N(n-1,n) - JpY(n,n+1)));
CpE(n+2,n+2)=376.7/sqrt(Er)*(2*Ya-Mn);
CpE(1,2)=376.7/sqrt(Er)*(M1-Ya);

for k=1:n-1
CpE(k+1,k+2)=376.7*h*Ya/sqrt(Er)*JpY(k+1,k+2);
end
CpE(n+1,n+2)=376.7/sqrt(Er)*(Mn-Ya);

%The width calculations for each interdigital line

for k=1:n+2
wpb(k)=(1-t/b)*CpE(k,k)/4;
if((wpb(k)/(1-t/b))<0.35)
wpb(k)=(0.07*(1-t/b)+wpb(k))/1.20;
end
w(k)=wpb(k)*b;
end

%The spacing calculations for two adjacent interdigital lines
%cannot be done by formulae. Gestsinger's or Cristal's charts must be used.

for k=1:n+1
if(CpE(k,k+1)>=6)
    if(t/b>=0.8)
        spb(k,k+1)=0.12;
    else if(t/b>=0.6)
            spb(k,k+1)=0.1;
        else if(t/b>=0.4)
                spb(k,k+1)=0.07;
            else if(t/b>=0.2)
                    spb(k,k+1)=0.04;
                else if(t/b>=0.1)
                        spb(k,k+1)=0.03;
                    end
                end
            end
        end
    end

else if(CpE(k,k+1)>=4)
       if(t/b>=0.8)
        spb(k,k+1)=0.175;
    else if(t/b>=0.6)
            spb(k,k+1)=0.14;
        else if(t/b>=0.4)
                spb(k,k+1)=0.1;
            else if(t/b>=0.2)
                    spb(k,k+1)=0.05;
                else if(t/b>=0.1)
                        spb(k,k+1)=0.03;
                    else if(t/b>=0.05)
                            spb(k,k+1)=0.02;
                        end
                    end
                end
            end
        end
       end

else if(CpE(k,k+1)>=2)
       if(t/b>=0.8)
        spb(k,k+1)=0.24;
    else if(t/b>=0.6)
            spb(k,k+1)=0.2;
        else if(t/b>=0.4)
                spb(k,k+1)=0.16;
            else if(t/b>=0.2)
                    spb(k,k+1)=0.1;
                else if(t/b>=0.1)
                        spb(k,k+1)=0.06;
                    else if(t/b>=0.05)
                            spb(k,k+1)=0.04;
                        end
                    end
                end
            end
        end
       end       

else if(CpE(k,k+1)>=1)
       if(t/b>=0.8)
        spb(k,k+1)=0.4;
    else if(t/b>=0.6)
            spb(k,k+1)=0.35;
        else if(t/b>=0.4)
                spb(k,k+1)=0.3;
            else if(t/b>=0.2)
                    spb(k,k+1)=0.25;
                else if(t/b>=0.1)
                        spb(k,k+1)=0.2;
                    else if(t/b>=0.05)
                            spb(k,k+1)=0.15;
                        else
                            spb(k,k+1)=0.1;
                        end
                        end
                    end
                end
            end
       end
else if(CpE(k,k+1)>=0.4)
       if(t/b>=0.8)
        spb(k,k+1)=0.65;
    else if(t/b>=0.6)
            spb(k,k+1)=0.6;
        else if(t/b>=0.4)
                spb(k,k+1)=0.55;
            else if(t/b>=0.2)
                    spb(k,k+1)=0.5;
                else if(t/b>=0.1)
                        spb(k,k+1)=0.45;
                    else if(t/b>=0.05)
                            spb(k,k+1)=0.38;
                        else
                            spb(k,k+1)=0.3;
                        end
                        end
                    end
                end
            end
       end           
    
else if(CpE(k,k+1)>=0.1)
       if(t/b>=0.8)
        spb(k,k+1)=1.0;
    else if(t/b>=0.6)
            spb(k,k+1)=0.95;
        else if(t/b>=0.4)
                spb(k,k+1)=0.9;
            else if(t/b>=0.2)
                    spb(k,k+1)=0.8;
                else if(t/b>=0.1)
                        spb(k,k+1)=0.7;
                    else if(t/b>=0.05)
                            spb(k,k+1)=0.62;
                        else
                            spb(k,k+1)=0.53;
                        end
                        end
                    end
                end
            end
       end           

else
       if(t/b>=0.8)
        spb(k,k+1)=1.5;
    else if(t/b>=0.6)
            spb(k,k+1)=1.45;
        else if(t/b>=0.4)
                spb(k,k+1)=1.3;
            else if(t/b>=0.2)
                    spb(k,k+1)=1.2;
                else if(t/b>=0.1)
                        spb(k,k+1)=1.1;
                    else if(t/b>=0.05)
                            spb(k,k+1)=1.05;
                        else
                            spb(k,k+1)=1.0;
                        end
                        end
                    end
                end
            end
       end
    end
    end
    end
    end
    end
end
end
    
for k=1:n+1
    s(k,k+1)=spb(k,k+1)*b;
end

%Displaying all the values
display(w);
%display(s);

%Plotting the actual top view of the filter
x=1;
C=[1 0 0];

for k=1:n+1
figure(2);
surf([3,3+l,3+l,3],[x,x+w(k),x+w(k),x],ones(4,4),'EdgeColor',C,'FaceColor',C);
xlabel('x(inches)');ylabel('y(inches)');zlabel('z(inches)');
hold on 
x=x+w(k)+s(k,k+1);
end
surf([3,3+l,3+l,3],[x,x+w(n+2),x+w(n+2),x],ones(4,4),'EdgeColor',C,'FaceColor',C);
hold off
end