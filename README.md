# NEDSS-EpiSync
![Logo](docs/logo.svg "EpiSync Logo")

A set of component services for:
- Creating EpiSync Data Dictionary
- Validating EpiSync CSV data
- Publishing EpiSync CSV data

## Quickstart

```bash
$ make build
$ make up
docker compose up -d
[+] Running 3/3
 ⠿ Container episync-db    Started                                                                                           1.0s
 ⠿ Container episync-test  Started                                                                                           1.0s
 ⠿ Container episync-dd    Started                  
```

Then open browser to http://localhost:8014/docs for the `dictionary` and `validate` service Swagger UI's

To bring down the episync stack:
```bash
$ make stop
docker compose stop
[+] Running 3/3
 ⠿ Container episync-dd    Stopped                                                                                           0.7s
 ⠿ Container episync-test  Stopped                                                                                           0.0s
 ⠿ Container episync-db    Stopped                                                                                           0.3s
```

