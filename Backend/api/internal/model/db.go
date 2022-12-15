package model

import (
	"database/sql"
	"fmt"

	_ "github.com/go-sql-driver/mysql"
	"gopkg.in/gorp.v1"
)

func InitDb() (*gorp.DbMap, error) {
	// db接続
	path := fmt.Sprintf(
		"%s:%s@tcp(%s)/%s?charset=utf8&parseTime=true",
		"root",     // username
		"passwd",   // password
		"hikky-db", // hostname
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
