# NEDSS-EpiSync
![Logo](docs/logo.svg "EpiSync Logo")

A set of component services for:
- Creating, Serving & Managing EpiSync Data Dictionary
- Validating EpiSync data
- Publishing EpiSync data

## Quickstart

```bash
$ make build
$ make up
docker compose up -d
[+] Running 5/5
 ⠿ Container episync-mvps-s3  Started                                                                                                                                                                                                                1.3s
 ⠿ Container episync-db       Started                                                                                                                                                                                                                1.2s
 ⠿ Container episync-dd       Started                                                                                                                                                                                                                1.3s
 ⠿ Container episync-test     Started                                                                                                                                                                                                                0.8s
 ⠿ Container episync-publish  Started                            
```

Then open browser to http://localhost:8014/docs for the `dictionary` and `validate` service Swagger UI's

To bring down the EpiSync stack:
```bash
$ make stop
docker compose stop
[+] Running 5/5
 ⠿ Container episync-publish  Stopped                                                                                                                                                                                                               10.5s
 ⠿ Container episync-mvps-s3  Stopped                                                                                                                                                                                                                4.3s
 ⠿ Container episync-test     Stopped                                                                                                                                                                                                                0.0s
 ⠿ Container episync-db       Stopped                                                                                                                                                                                                                0.5s
 ⠿ Container episync-dd       Stopped                                                                                                                                                                                                                0.8s                                                                              0.3s
```

## Architecture
Below is the current architecture for EpiSync Data Dictionary that addresses the following requirements:
- Data Elements Stored in secure database, managed by existing tooling and best-practices
- Allow multiple users to access and manage the data dictionary without conflict, data integrity errors, or other similar concerns
- Allow stored data dictionary elements to be serialized into various formats such as JSON, CSV, HTML etc
- Fully dynamic and data drive (i.e. nothing is static)
- Supports transactional modifications
- Supports data element value constraints
- Re-uses existing architecture components and technology
- Cloud neutral

![Architecture](docs/arch.png "EpiSync Architecture")