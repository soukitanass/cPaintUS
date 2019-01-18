Version du document: 
17 janvier 2019


****************************************************************************
ÉTAPE 1:
****************************************************************************

S'assurer que la version Java JDK 9.0.4 est installé sur le système
Lien pour téléchargement: 

https://www.oracle.com/technetwork/java/javase/downloads/java-archive-javase9-3934878.html


****************************************************************************
ÉTAPE 2:
****************************************************************************

Installer e(fx)clipse dans l'environnement Eclipse.

1. Menu > Help > Install New Software...
2. Dans le menu déroulant, sélectionner < 2018-12 - http://download.eclipse.org/releases/2018-12 >
3. Dans le champ texte < type filer text >, inscrire: e(fx)clipse
4. Lorsque le composant sera trouvé, Cocher se module, puis cliquer sur Next.
5. Accepter la licence puis finaliser l'installation
6. Un redémarrage de Ecipse sera requis pour finaliser l'installation


****************************************************************************
ÉTAPE 3:
****************************************************************************

Définir Java 9, comme librairie par défaut pour les projets sur Eclipse

1. Menu > Window > Preferences
2. Java > Installed JREs
3. Optionnel: Si jdk-9.0.4 n'est pas présent dans la liste, l'ajouter via le bouton < Add >
4. Sélectionner la version de jdk: 9.0.4
5. Cliquer sur le bouton < Apply >


****************************************************************************
ÉTAPE 4:
****************************************************************************

Importer le projet en tant que projet Gradle.

1. Menu > File > Import...
2. Gradle > Existing Gradle project...
3. Next > Next
4. < Browse >, puis sélectionner l'emplacement physique de ce projet
5. Cliquer < Finish >
5. Attendre que le chargement des composants Gradle du projet soit complété


****************************************************************************
ÉTAPE 5:
****************************************************************************

S'assurer que le jdk-9.0.4 est défini par défaut pour le projet.

1. Effectuer un clique droit de la souris sur le projet (fenêtre: Project Explorer)
2. Sélectionner: Build Path > Configure Build Path...
3. Sélectionner l'onglet  < Libraries >, puis l'option < JRE System Library >
4. Cliquer sur le bouton < Edit >
5. Puis s'assurer que le champ < Execution Environment >, pointe sur la librairie < JavaSE-1.8 (jdk-9.0.4) >
6. Si ce n'est pas le cas, sélectionné la librairie dans le menu déroulant puis cliquer sur < Finish >


****************************************************************************
ERREUR POSSIBLE LORS DE L'EXÉCUTION:
****************************************************************************

Error: LinkageError occurred while loading main class sample.Main
	java.lang.UnsupportedClassVersionError: sample/Main has been compiled by a more recent 
	version of the Java Runtime (class file version 55.0), this version of the Java Runtime 
	only recognizes class file versions up to 53.0

RÉSOLUTION:

Dans ce contexte, il faut sassurer que l'application est configuré pour fonctionner avec Java 9.

1. Faire un clique droit de la souris sur le dossier du projet (fenêtre: Project Explorer)
2. Sélectionner: Properties
3. Sélectionner: Java Compiler
4. S'assurer que la version 9.0.4 de Java est sélectionné
	