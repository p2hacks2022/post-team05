package db

import (
	"database/sql"
	"fmt"

	"gopkg.in/gorp.v1"
)

func InitDb() (*gorp.DbMap, error) {
	// db接続
	path := fmt.Sprintf(
		"%s:%s@tcp(127.0.0.1:3306)/%s?charset=utf8&parseTime=true",
		"root",     // username
		"passwd",   // password
		"hikky-db", // database
	)
	db, err := sql.Open("mysql", path)
	if err != nil {
		return nil, err
	}

	// dbをdbmapに
	dbmap := &gorp.DbMap{
		Db:      db,
		Dialect: gorp.MySQLDialect{},
	}

	return dbmap, nil
}
