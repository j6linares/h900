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
[master c990819] README.md actualizado

$ git config --global user.name "j6linares"
$ git config --global user.email j6linares@gmail.com

subir al repositorio github
$ git push https://github.com/j6linares/h900 

mostrar las ramas
$ git branch -v
* master c990819 [ahead 1] README.md actualizado

$ git push 
Username for 'https://github.com': j6linares
Password for 'https://j6linares@github.com': 
Counting objects: 3, done.
Delta compression using up to 4 threads.
Compressing objects: 100% (3/3), done.
Writing objects: 100% (3/3), 798 bytes | 798.00 KiB/s, done.
Total 3 (delta 0), reused 0 (delta 0)
To https://github.com/j6linares/h900
   9ffbb8c..c990819  master -> master