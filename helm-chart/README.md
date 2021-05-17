# Touitter App Helm chart

## Installing the Chart

The chart requires the following external dependencies:
* Access to docker registry with custom images of the Touitter services (webapp, api and ml service).
* [PostgreSQL](https://www.postgresql.org/) instance with a created database for Touitter
* [RabbitMQ](https://www.rabbitmq.com/) instance with a created database for Touitter
* [Keycloak](https://www.keycloak.org) instance with valid application mappings for Touitter.

To install the chart with the release name `touitter-dev` with default values ():

``` bash
helm install touitter-dev ./helm-chart
```

For production uses to install chart with custom `values-prod.yaml` values file:

```bash
helm install -f helm-chart/values-prod.yaml touitter-dev ./helm-chart
```