# cPaintUS
S7H19AgileC

À noter pour les tests : Il est important de rouler les tests dans cpaintus.models séparément des tests de cpaintus.views (TestsFX), car ces derniers ont un impact sur les autres à cause que nous ne les roulons pas en headless.

De plus, pour le coverage, lorsqu'on le roule localement, nous obtenons un coverage plus haut que 70%. Cependant, même après avoir exporté vers Sonar, Sonar dit que nous ne sommes qu'à 63.4% pour une raison inconnue.