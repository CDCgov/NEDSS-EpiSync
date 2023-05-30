import boto3
import io

session = boto3.session.Session()

s3_client = session.client(
    service_name='s3',
    aws_access_key_id='minioadmin',
    aws_secret_access_key='minioadmin',
    endpoint_url='http://172.30.0.3:9000'
)
print(s3_client.list_buckets(name='episync'))
fo = io.BytesIO(b'my data stored as file object in RAM')
s3_client.upload_fileobj(fo, 'mvps', '/data1')
