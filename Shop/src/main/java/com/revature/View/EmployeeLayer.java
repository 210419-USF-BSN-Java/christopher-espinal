package com.revature.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.ServicesImpl.EmployeeServicesImpl;
import com.revature.models.Group;
import com.revature.models.Item;
import com.revature.models.Offer;
import com.revature.models.Payment;
import com.revature.models.Status;
import com.revature.models.User;

public class EmployeeLayer {
	private static Logger log = LogManager.getRootLogger();
	private static Group employeeGroup = new Group(3, "employee");
	private static EmployeeServicesImpl eService = new EmployeeServicesImpl();

	public void mainEmployeeDashboard(User user, Group group, Scanner sc) {
		boolean run = true;
		do {
			log.info("Welcome to the Employee Dashboard\n");
			log.info("Choose an option:");
			log.info("		Press 1 to Manage Store Items");
			log.info("		Press 2 to Manage Offers");
			log.info("		Press 3 to See Customer Payments");
			log.info("		Press anything else to exit");
			String choice = sc.nextLine();
			switch (choice) {
			case "1":
				manageStoreItems(user, sc);
				break;
			case "2":
				manageOffers(user, sc);
				break;
			case "3":
				seeCustomerPayments(user, sc);
				break;
			default:
				log.info("\nGoing back to the NYC Grocery Store Homepage\n");
				run = false;
				break;
			}
		} while (run);
	}

	private void seeCustomerPayments(User user, Scanner sc) {
		// TODO Auto-generated method stub
		boolean run = true;

		// see all offers

		do {
			log.info("\nSee all Payments\n");
			List<Payment> payments = eService.getAllPayments();
			payments.stream().forEach(p -> log.info(p));

			log.info("\nYou Saw All of Your Payments\n");
			log.info("Choose an option:");
			log.info("		Press anything else to revisit the MAIN EMPLOYEE DASHBOARD");
			String choice = sc.nextLine();
			switch (choice) {
			default:
				log.info("\nGoing back to the MAIN EMPLOYEE DASHBOARD\n");
				run = false;
				break;
			}
		} while (run);

	}

	private void manageOffers(User user, Scanner sc) {
		// TODO Auto-generated method stub
		Status pending = new Status();
		pending.setStatus_id(1);
		pending.setStatus_name("pending");
		/*
		 * Status completed = new Status(); pending.setStatus_id(2);
		 * pending.setStatus_name("completed"); Status rejected = new Status();
		 * pending.setStatus_id(1); pending.setStatus_name("rejected");
		 */
		boolean run = true;

		// see all offers

		do {
			log.info("\nAll Current Offers\n");
			List<Offer> offers = eService.seeAllOffers();
			offers.stream().filter(p -> (p.getStatus().equals(pending) && (p.getOwnership() == false)))
					.forEach(p -> log.info(p));

			log.info("\nManage Your Offers\n");
			log.info("Choose an option:");
			log.info("		Press 1 to Accept an Offer");
			log.info("		Press 2 to Reject an Offer");
			log.info("		Press 3 to See All Offers");
			log.info("		Press anything else to revisit the EMPLOYEE DASHBOARD");
			String choice = sc.nextLine();
			switch (choice) {
			case "1":
				acceptAnOffer(user, sc);
				break;
			case "2":
				rejectAnOffer(user, sc);
				break;
			case "3":
				seeAllOffers(user, sc);
				break;
			default:
				log.info("\nGoing back to the EMPLOYEE DASHBOARD\n");
				run = false;
				break;
			}
		} while (run);

	}

	private void seeAllOffers(User user, Scanner sc) {
		boolean run = true;

		// see all offers

		do {
			log.info("\nSee all Offers\n");
			List<Offer> offers = eService.seeAllOffers();
			offers.stream().forEach(p -> log.info(p));

			log.info("\nSee All Offers\n");
			log.info("Choose an option:");
			log.info("		Press anything else to revisit the MANAGE OFFERS DASHBOARD");
			String choice = sc.nextLine();
			switch (choice) {
			default:
				log.info("\nGoing back to the MANAGE OFFERS DASHBOARD\n");
				run = false;
				break;
			}
		} while (run);

	}

	private void rejectAnOffer(User user, Scanner sc) {
		boolean run = true;

		// see all offers

		do {
			log.info("\nReject an Offer\n");
			log.info("\nType in the Offer ID:\n");
			Integer item_id = Integer.parseInt(sc.nextLine());
			Offer offer = eService.getOfferById(item_id);

			int affected = eService.respondToOffer(offer, "reject");
			log.info("\nThe Result:\n" + offer.toString());

			log.info("\nManage Your Offers\n");
			log.info("Choose an option:");
			log.info("		Press 1 to Reject another Offer");
			log.info("		Press anything else to revisit the MANAGE OFFERS DASHBOARD");
			String choice = sc.nextLine();
			switch (choice) {
			case "1":
				break;
			default:
				log.info("\nGoing back to the MANAGE OFFERS DASHBOARD\n");
				run = false;
				break;
			}
		} while (run);

	}

	private void acceptAnOffer(User user, Scanner sc) {
		// TODO Auto-generated method stub
		/*
		 * Status pending = new Status(); pending.setStatus_id(1);
		 * pending.setStatus_name("pending");
		 */

		/*
		 * Status completed = new Status(); pending.setStatus_id(2);
		 * pending.setStatus_name("completed"); Status rejected = new Status();
		 * pending.setStatus_id(1); pending.setStatus_name("rejected");
		 */
		boolean run = true;

		// see all offers

		do {
			log.info("\nAccept an Offer\n");
			log.info("\nWARNING: Accepting an Offer will Reject All other Offers for this Item\n");
			log.info("\nType in the Offer ID:\n");
			Integer item_id = Integer.parseInt(sc.nextLine());
			Offer offerToAccept = eService.getOfferById(item_id);

			int affected = eService.respondToOffer(offerToAccept, "accept");
			log.info("\nThe Result:\n" + offerToAccept.toString());

			log.info("\nManage Your Offers\n");
			log.info("Choose an option:");
			log.info("		Press 1 to Accept another Offer");
			log.info("		Press anything else to revisit the MANAGE OFFERS DASHBOARD");
			String choice = sc.nextLine();
			switch (choice) {
			case "1":
				break;
			default:
				log.info("\nGoing back to the MANAGE OFFERS DASHBOARD\n");
				run = false;
				break;
			}
		} while (run);

	}

	public void manageStoreItems(User user, Scanner sc) {
		// add items
		// remove items
		boolean run = true;
		do {
			log.info("Store Inventory\n");
			List<Item> items = eService.seeItemsOnSale();
			items.stream().forEach(item -> log.info(item));

			log.info("Manage Store Items\n");

			log.info("Choose an option:");
			log.info("		Press 1 to Add Store Items");
			log.info("		Press 2 to Remove Store Items");
			log.info("		Press anything else to go to the MAIN EMPLOYEE DASHBOARD");
			String choice = sc.nextLine();
			switch (choice) {
			case "1":
				addStoreItem(user, sc);
				break;
			case "2":
				removeStoreItem(user, sc);
				break;
			default:
				log.info("\nGoing back to the EMPLOYEE DASHBOARD\n");
				run = false;
				break;
			}
		} while (run);
	}

	public void addStoreItem(User user, Scanner sc) {
		// add items
		// remove items
		boolean run = true;
		do {
			log.info("Add an Item\n");

			Map<String, Object> itemArgs = new HashMap<>();

			log.info("\nType in the Item Name:\n");
			String item_name = sc.nextLine();
			itemArgs.put("item_name", item_name);

			log.info("\nType in the Item Price:\n");
			Double item_price = Double.parseDouble(sc.nextLine());
			itemArgs.put("item_price", item_price);

			Item item = eService.addItem(itemArgs);
			log.info("\nThe following is the result:\n");
			log.info("		" + item + "\n");

			log.info("Choose an option:");
			log.info("		Press 1 to add another item");
			log.info("		Press anything else to go to the STORE INVENTORY");
			String choice = sc.nextLine();
			switch (choice) {
			case "1":
				break;
			default:
				log.info("\nGoing back to the STORE INVENTORY\n");
				run = false;
				break;
			}
		} while (run);
	}

	public void removeStoreItem(User user, Scanner sc) {
		// add items
		// remove items
		boolean run = true;
		do {
			log.info("Remove an Item\n");

			log.info("\nType in the Item ID:\n");
			Integer item_id = Integer.parseInt(sc.nextLine());
			Item itemToDelete = eService.getItemById(item_id);

			int affected = eService.removeItem(itemToDelete);

			log.info("Choose an option:");
			log.info("		Press 1 to remove another item");
			log.info("		Press anything else to go to the STORE INVENTORY");
			String choice = sc.nextLine();
			switch (choice) {
			case "1":
				break;
			default:
				log.info("\nGoing back to the STORE INVENTORY\n");
				run = false;
				break;
			}
		} while (run);
	}

}
