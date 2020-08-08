# Määrittelydokumentti

## Idea
Luolastogeneraattori luo jonkinlaisen "luolan" tai kentän. Tarkoituksena on tehdä ennaltageneroituja ja pelattaessa kehittyviä kenttiä.
Generaattori on pohjana jonkinlaiselle pelille tai monelle erilaiselle testi pelille.

Ohjelma ottaa erilaisia syötteitä jotka määrittävät minkälainen kenttä generoidaan. Esimerkiksi sen koko, mahdolliset viholliset, esineet/asiat joita pelaaja voi käsitellä.
Kentät tulee joko tallentaa (pysyvästi tai tilapäisesti) tai pystyä generoida samalla tai hiukan eri tavalla uudestaan (jotta voi palata aiempaan kenttään/luolaan).

Tavoitteena on, että maksimissa vain yksi lyhyehkö latausruutu ja muuten luolan generointi ei ole pelaajalle ilmiselvää.

## Toteutus
Luolastogeneraattorilla on luokka, joka luo kentän, kuten kerros tai pelin leveli. Kerroksella on huone luokka, tämä huone on kerroksen pohja.
Huone luokka luo verkon johon se voi satunnaisesti annetun leveys ja pituus muuntujien avulla luoda huoneen. Huoneen sisällön voi arpoa.
Huone merkitsee myös keskipisteensä.
Kentän huone on tyhjä, sen päälle sijoitetaan satunnaisesti luodut huoneet. Huoneiden verkko koostuu tiileistä, joka on oma luokkansa.
Tiili luokalla on tyyppi ja visuaalinen esitystapa. Tyyppi voi olla 0 joka merkitsee muuria ja visuaalinen muuntuja voi olla X (toistaiseksi).
kerroksella on myös verkko jossa on soluja, myös oma luokka. Verkko määrittää solujen paikat, koordinaatit. Solut ovat satunnaisesti joko käytössä tai ei.
Huoneet luodaan ja siojoitetaan soluihin satunnaisesti. Kun solussa on huone, se muuttuu aktiiviseksi, eikä sille voi asettaa toista huonetta.
Lopuksi on käytävä luokka, joka luo käytävät huoneiden välille. Tämä onnistuu katsomalla aktiivit solut, tarkastamalla niiden huoneiden keskipisteet 
ja yhdistämällä näitä käytävillä.

## Lähteet

Apuna käytetty http://roguebasin.roguelikedevelopment.org/index.php?title=Grid_Based_Dungeon_Generator
