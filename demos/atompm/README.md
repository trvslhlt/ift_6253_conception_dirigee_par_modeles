# AToMPM

[https://atompm.github.io/](https://atompm.github.io/)

## Running the demo

This assumes you already have the `atompmvm:latest` image built locally (see the
[AToMPM Docker packaging instructions](https://github.com/AToMPM/atompm/tree/master/packaging/docker)
for how to build it from source).

1. Build the demo image (a thin wrapper around `atompmvm` that adds a volume for
   persisting your models):

   ```bash
   docker build -t atompm-demo .
   ```

2. Run the container, mapping the AToMPM port and using a named volume for
   persistent user data:

   ```bash
   docker run --name atompm-demo -p 8124:8124 -v atompm-demo-users:/opt/atompm/users atompm-demo
   ```

   A named volume (rather than a bind-mounted host folder) is required here: AToMPM
   seeds new accounts by copying `users/(default)` inside the image, and a bind
   mount to an empty host folder would hide that template and break account
   creation. Docker initializes a named volume from the image's existing directory
   contents on first run, so `(default)` is preserved.

3. Open [http://localhost:8124/atompm](http://localhost:8124/atompm) in Firefox or
   Chrome.

4. Stop the container with `Ctrl+C`, or from another terminal:

   ```bash
   docker stop atompm-demo
   ```

   To resume it later: `docker start -a atompm-demo`. To remove the container
   (this does not delete the `atompm-demo-users` volume): `docker rm atompm-demo`.
   To delete the persisted data too: `docker volume rm atompm-demo-users`.

