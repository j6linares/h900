# h900
h900
clonar el repositorio github en local
$ git clone https://github.com/j6linares/h900

estado del repositorio
$ git log --oneline
9ffbb8c (HEAD -> master, origin/master, origin/HEAD) -new.sql
67469f1 +new.sql
20f1267 h9cp.sql
f2e2223 h9cp
4da099b README.md

$ git status
On branch master
Your branch is up to date with 'origin/master'.

Changes not staged for commit:
  (use "git add <file>..." to update what will be committed)
  (use "git checkout -- <file>..." to discard changes in working directory)

	modified:   README.md

Untracked files:
  (use "git add <file>..." to include in what will be committed)

	h9cp/.classpath
	h9cp/.project
	h9cp/.settings/
	h9cp/target/

no changes added to commit (use "git add" and/or "git commit -a")

Registrar el fichero al control de versiones
$ git add README.md

commit al repositorio local
$ git commit -m "README.md actualizado"


