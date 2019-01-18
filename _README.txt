Version du document: 
17 janvier 2019


****************************************************************************
�TAPE 1:
****************************************************************************

S'assurer que la version Java JDK 9.0.4 est install� sur le syst�me
Lien pour t�l�chargement: 

https://www.oracle.com/technetwork/java/javase/downloads/java-archive-javase9-3934878.html


****************************************************************************
�TAPE 2:
****************************************************************************

Installer e(fx)clipse dans l'environnement Eclipse.

1. Menu > Help > Install New Software...
2. Dans le menu d�roulant, s�lectionner < 2018-12 - http://download.eclipse.org/releases/2018-12 >
3. Dans le champ texte < type filer text >, inscrire: e(fx)clipse
4. Lorsque le composant sera trouv�, Cocher se module, puis cliquer sur Next.
5. Accepter la licence puis finaliser l'installation
6. Un red�marrage de Ecipse sera requis pour finaliser l'installation


****************************************************************************
�TAPE 3:
****************************************************************************

D�finir Java 9, comme librairie par d�faut pour les projets sur Eclipse

1. Menu > Window > Preferences
2. Java > Installed JREs
3. Optionnel: Si jdk-9.0.4 n'est pas pr�sent dans la liste, l'ajouter via le bouton < Add >
4. S�lectionner la version de jdk: 9.0.4
5. Cliquer sur le bouton < Apply >


****************************************************************************
�TAPE 4:
****************************************************************************

Importer le projet en tant que projet Gradle.

1. Menu > File > Import...
2. Gradle > Existing Gradle project...
3. Next > Next
4. < Browse >, puis s�lectionner l'emplacement physique de ce projet
5. Cliquer < Finish >
5. Attendre que le chargement des composants Gradle du projet soit compl�t�


****************************************************************************
�TAPE 5:
****************************************************************************

S'assurer que le jdk-9.0.4 est d�fini par d�faut pour le projet.

1. Effectuer un clique droit de la souris sur le projet (fen�tre: Project Explorer)
2. S�lectionner: Build Path > Configure Build Path...
3. S�lectionner l'onglet  < Libraries >, puis l'option < JRE System Library >
4. Cliquer sur le bouton < Edit >
5. Puis s'assurer que le champ < Execution Environment >, pointe sur la librairie < JavaSE-1.8 (jdk-9.0.4) >
6. Si ce n'est pas le cas, s�lectionn� la librairie dans le menu d�roulant puis cliquer sur < Finish >


****************************************************************************
ERREUR POSSIBLE LORS DE L'EX�CUTION:
****************************************************************************

Error: LinkageError occurred while loading main class sample.Main
	java.lang.UnsupportedClassVersionError: sample/Main has been compiled by a more recent 
	version of the Java Runtime (class file version 55.0), this version of the Java Runtime 
	only recognizes class file versions up to 53.0

R�SOLUTION:

Dans ce contexte, il faut sassurer que l'application est configur� pour fonctionner avec Java 9.

1. Faire un clique droit de la souris sur le dossier du projet (fen�tre: Project Explorer)
2. S�lectionner: Properties
3. S�lectionner: Java Compiler
4. S'assurer que la version 9.0.4 de Java est s�lectionn�
	