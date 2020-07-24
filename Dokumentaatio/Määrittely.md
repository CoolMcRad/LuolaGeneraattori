# Määrittelydokumentti
Luolastogeneraattori luo jonkinlaisen "luolan" tai kentän. Tarkoituksena on tehdä ennaltageneroituja ja pelattaessa kehittyviä kenttiä.
Generaattori on pohjana jonkinlaiselle pelille tai monelle erilaiselle testi pelille.

Ohjelma ottaa erilaisia syötteitä jotka määrittävät minkälainen kenttä generoidaan. Esimerkiksi sen koko, mahdolliset viholliset, esineet/asiat joita pelaaja voi käsitellä.
Kentät tulee joko tallentaa (pysyvästi tai tilapäisesti) tai pystyä generoida samalla tai hiukan eri tavalla uudestaan (jotta voi palata aiempaan kenttään/luolaan).

Tavoitteena on, että maksimissa vain yksi lyhyehkö latausruutu ja muuten luolan generointi ei ole pelaajalle ilmiselvää.
