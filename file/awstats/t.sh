#!/bin/bash
name=access.log.$(date -d "yesterday" +"%Y")$(date -d "yesterday" +"%m")$(date -d "yesterday" +"%d")_;
t=0;
c=10;
echo $name;
for i in {0..23}
do
    if [ $i -lt 10 ] 
    then
        ./copyfrom.exp 10.135.35.160 mqq mqq2005 /usr/local/app/resin34/work/logs/$name$t$i /data/resin10log/logs/$name$t$i
    else
         ./copyfrom.exp 10.135.35.160 mqq mqq2005 /usr/local/app/resin34/work/logs/$name$i /data/resin10log/logs/$name$i
    fi
done

/usr/local/app/resin34/work/awstats/tools/awstats_buildstaticpages.pl -config=webpim -update -lang=cn -awstatsprog=/usr/local/app/resin34/work/awstats/wwwroot/cgi-bin/awstats.pl -dir=/usr/local/app/resin34/work/data/html
rm /usr/local/app/resin34/work/logs/*

for i in {0..23}
do
     if [ $i -lt 10 ]
     then 
         ./copyfrom.exp 10.135.37.218 mqq mqq2005 /usr/local/app/resin34/work/logs/$name$t$i /data/resin10log/logs/$name$t$i
     else
         ./copyfrom.exp 10.135.37.218 mqq mqq2005 /usr/local/app/resin34/work/logs/$name$i /data/resin10log/logs/$name$i
     fi  
done

/usr/local/app/resin34/work/awstats/tools/awstats_buildstaticpages.pl -config=webpim -update -lang=cn -awstatsprog=/usr/local/app/resin34/work/awstats/wwwroot/cgi-bin/awstats.pl -dir=/usr/local/app/resin34/work/data/html 
rm /usr/local/app/resin34/work/logs/*

for i in {0..23}
do
     if [ $i -lt 10 ]
     then 
        ./copyfrom.exp 10.135.15.212 mqq mqq2005 /usr/local/app/resin34/work/logs/$name$t$i /data/resin4log/logs/$name$t$i
     else
        ./copyfrom.exp 10.135.15.212 mqq mqq2005 /usr/local/app/resin34/work/logs/$name$i /data/resin4log/logs/$name$i
     fi  
done

/usr/local/app/resin34/work/awstats/tools/awstats_buildstaticpages.pl -config=webpim -update -lang=cn -awstatsprog=/usr/local/app/resin34/work/awstats/wwwroot/cgi-bin/awstats.pl -dir=/usr/local/app/resin34/work/data/html
rm /usr/local/app/resin34/work/logs/*

for i in {0..23}
do
    if [ $i -lt 10 ]
    then                                                                                                                            ./copyfrom.exp 10.135.39.20 mqq mqq2005 /usr/local/app/resin34/work/logs/$name$t$i /data/resin4log/logs/$name$t$i   
    else
        ./copyfrom.exp 10.135.39.20 mqq mqq2005 /usr/local/app/resin34/work/logs/$name$i /data/resin4log/logs/$name$i                                   
    fi
done                                                                                                                                          
/usr/local/app/resin34/work/awstats/tools/awstats_buildstaticpages.pl -config=webpim -update -lang=cn -awstatsprog=/usr/local/app/resin34/work/awstats/wwwroot/cgi-bin/awstats.pl -dir=/usr/local/app/resin34/work/data/html
rm /usr/local/app/resin34/work/logs/*
