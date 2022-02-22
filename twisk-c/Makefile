cc := gcc
cflags := -Wall -fPIC
clibflags := -shared
lib := libTwisk.so
files := programmeC.o
target := main
client:=client.o

$(target):  $(lib) main.c
	@$(cc) -Wall -L. main.c -o $(target) -lTwisk
	@echo "Fichier cible '$(target)' généré !"

$(lib): $(files) $(client)
	@echo "Génération de la librairie '$(lib)' ..."
	@$(cc) $(clibflags) $^ -o $@
	@echo "Répertoire de la librairie dynamique mis à jour !"

%.o: %.c
	@echo "Compilation du code C ..."
	@$(cc) $(cflags) -c $<

test:
	@nm -D $(lib)

clean:
	@echo "Suppression du client et de la cible ..."
	@rm client.o $(target) $(lib) $(client)