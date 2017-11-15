package com.geog.DAO;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.geog.Model.HeadOfState;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class HeadOfStateDAO extends DAOFactory<HeadOfState> {
	// The mongodb collection
	private MongoCollection<Document> collection;

	public HeadOfStateDAO() {
		// This will cause the load of mysql database
		super();
		// Load the mongo database
		super.loadMongoDBDatabase();
		// get the collection
		this.collection = super.getMongodatabase().getCollection("headsOfState")
				// Get the database to throw exception on id duplication
				.withWriteConcern(WriteConcern.ACKNOWLEDGED);
	}

	@Override
	public HeadOfState find(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HeadOfState> list() {
		// Create an empty list
		List<HeadOfState> list = new ArrayList<HeadOfState>();
		// Get the content of the collection
		FindIterable<Document> items = this.collection.find();
		// Loop the items
		for (Document d : items) {
			// Create new head of state
			HeadOfState hos = new HeadOfState();
			// Set attributes
			hos.setCode(d.getString("_id"));
			hos.setHeadOfState(d.getString("headOfState"));
			// Add to the list
			list.add(hos);
		}
		// Return the list
		return list;
	}

	@Override
	public boolean update(HeadOfState object) {
		// Make sure the id is not empty
		if (!object.getCode().isEmpty()) {
			//Create a new document
			BasicDBObject newDocument = new BasicDBObject();
			newDocument.put("headOfState", object.getHeadOfState());
			//Create a new document to match the update on.
			BasicDBObject searchQuery = new BasicDBObject().append("_id", object.getCode());
			//Update the document. It will replace the whole document, which is not an issue now
			this.collection.updateOne(searchQuery, newDocument);
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(HeadOfState object) {
		// Make sure the id is not empty
		if (!object.getCode().isEmpty()) {
			// Delete the one
			this.collection.deleteOne(new BasicDBObject("_id", object.getCode()));
			return true;
		}

		return false;
	}

	@Override
	public boolean insert(HeadOfState object) throws MongoException {
		// CReate a new db document
		Document doc = new Document();
		// Add the attributes
		doc.append("_id", object.getCode()).append("headOfState", object.getHeadOfState());
		// Insert to the collection. Throws exception on duplicates
		this.collection.insertOne(doc);
		// Return true
		return true;
	}

}
