## EpiSync CLI

### Quickstart

```bash
$ make init
$ make install
```
Now, go to https://docs.google.com/spreadsheets/d/1KUaSyvDeLTMC3U0GuZISg130hiVOfhGHHZGtx3nhK9c/edit#gid=2079903563
and File->Download->Microsoft Excel and save to your local disk
```bash
$ vi episync.ini
```
Edit your .ini file to point to your downloaded .xlsx file
```ini
[mmg]
file=~/episync-dd.xlsx
```
Now run the episync create ddl create command
```bash
$ source venv/bin/activate
(venv) $ episync ddl create
```