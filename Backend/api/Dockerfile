FROM golang:1.18 as build

WORKDIR /go/src/app
COPY . .

RUN go mod download
RUN CGO_ENABLED=0 GOOS=linux GOARCH=amd64 go build -o /go/bin/app ./main.go

# multi-staged build
FROM gcr.io/distroless/base-debian11
COPY --from=build /go/bin/app /app
EXPOSE 8080
CMD ["/app"]
