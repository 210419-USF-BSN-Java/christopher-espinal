package com.revature.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.ServicesImpl.CustomerServicesImpl;
import com.revature.models.Group;
import com.revature.models.Item;
import com.revature.models.Offer;
import com.revature.models.Payment;
import com.revature.models.User;

public class CustomerLayer {
	private static Logger log = LogManager.getRootLogger();
	private static Group customerGroup = new Group(1, "customer");
	private static CustomerServicesImpl cService = new CustomerServicesImpl();

	public void mainCustomerDashboard(User user, Group group, Scanner sc) {
		boolean run = true;
		do {
			log.info("\nWelcome to the Customer Dashboard\n");
			log.info("Choose an option:");
			log.info("		Press 1 to Shop");
			log.info("		Press 2 to View Purchased Items");
			log.info("		Press 3 to Manage Payments");
			log.info("		Press anything else to exit");
			String choice = sc.nextLine();
			switch (choice) {
			case "1":
				goShopping(user, sc);
				break;
			case "2":
				viewPurchases(user, sc);
				break;
			case "3":
				managePayments(user, sc);
				break;
			default:
				log.info("\nGoing back to the NYC Grocery Store Homepage\n");
				run = false;
				break;
			}
		} while (run);
	}

	private void managePayments(User user, Scanner sc) {
		// TODO Auto-generated method stub
		boolean run = true;
		do {
			log.info("\nThe Following are your Purchases\n");
			List<Offer> offers = cService.seeAllOwnedOffers(user);
			offers.stream().filter(offer -> offer.getStatus().getStatus_name().equals("pending"))
					.forEach(offer -> log.info(offer.toString()));

			log.info("\nChoose an option:");
			log.info("		Press 1 to See Payments for an Item");
			log.info("		Press anything else to return to the CUSTOMER DASHBOARD");
			String choice = sc.nextLine();
			switch (choice) {
			case "1":
				seePayments(user, sc);
				break;
			default:
				log.info("\nGoing back to the CUSTOMER DASHBOARD\n");
				run = false;
				break;
			}
		} while (run);

	}

	private void seePayments(User user, Scanner sc) {
		// TODO Auto-generated method stub
		boolean run = true;
		do {
			log.info("\nPayments Dashboard\n");

			Offer offer;
			try {
				log.info("Select an Offer by typing in the id:\n");
				log.info("		Or press ENTER to quit\n");
				Integer offer_id = Integer.parseInt(sc.nextLine());
				offer = cService.getOfferById(offer_id);
				log.info("Current Offer: " + offer + "\n");
				List<Payment> payments = cService.viewUnPaidPayments(user, offer);
				payments.stream().forEach(p -> log.info(p.toString()));

				log.info("\nChoose an option:");
				log.info("		Press 1 to Make a Payment on an Item");
				log.info("		Press anything else to return to the PURCHASES DASHBOARD");
				String choice = sc.nextLine();
				switch (choice) {
				case "1":
					if (payments.size() > 0) {
						makePayment(user, sc, payments.get(0));
					} else {
						log.info("\nSorry, payments can't be made on this offer!\n");
						run = false;
					}
					break;
				default:
					log.info("\nGoing back to the PURCHASES DASHBOARD\n");
					run = false;
					break;
				}
			} catch (NumberFormatException n) {
				log.info("\nPlease type in a valid number. Start all over again.\n");
				break;
			}

		} while (run);

	}

	private void makePayment(User user, Scanner sc, Payment payment) {
		// TODO Auto-generated method stub
		boolean run = true;
		do {
			log.info("\nPayment Processing\n");
			log.info("Current Payment: " + payment + "\n");

			Map<String, Object> makePaymentArgs = new HashMap<>();
			makePaymentArgs.put("offer", payment.getOffer());
			makePaymentArgs.put("user", user);
			makePaymentArgs.put("amount", payment.getOffer().getBalance());
			cService.makePayment(makePaymentArgs);

			log.info("\nPayment is complete:");
			log.info("		Press anything else to exit");
			String choice = sc.nextLine();
			switch (choice) {
			default:
				log.info("\nGoing back to the PAYMENTS DASHBOARD\n");
				run = false;
				break;
			}
		} while (run);
	}

	private void viewPurchases(User user, Scanner sc) {
		// TODO Auto-generated method stub

		boolean run = true;
		do {
			log.info("The Following are your Purchases\n");
			List<Item> purchased = cService.seeOwnedItems(user);
			purchased.stream().forEach(item -> log.info(item.toString()));

			log.info("Choose an option:");
			log.info("		Press anything else to exit");
			String choice = sc.nextLine();
			switch (choice) {
			default:
				log.info("\nGoing back to the Customer Dashboard\n");
				run = false;
				break;
			}
		} while (run);
	}

	private void goShopping(User user, Scanner sc) {
		// TODO Auto-generated method stub
		boolean run = true;
		do {
			log.info("Store Inventory\n");
			List<Item> items = cService.seeItemsOnSale();
			items.stream().forEach(item -> log.info(item));

			log.info("Welcome to the NYC Grocery Store\n");

			log.info("Choose an option:");
			log.info("		Press 1 to Make an Offer on an Item");
			log.info("		Press anything else to go to the MAIN CUSTOMER DASHBOARD");
			String choice = sc.nextLine();
			switch (choice) {
			case "1":
				makeAnOffer(user, sc);
				break;
			default:
				log.info("\nGoing back to the CUSTOMER DASHBOARD\n");
				run = false;
				break;
			}
		} while (run);

	}

	private void makeAnOffer(User user, Scanner sc) {
		// TODO Auto-generated method stub
		boolean run = true;
		do {
			log.info("Make an Offer on an Item\n");

			Map<String, Object> makeOfferArgs = new HashMap<>();

			log.info("\nType in the Item ID:\n");
			Integer item_id = Integer.parseInt(sc.nextLine());
			Item item = cService.getItemById(item_id);
			makeOfferArgs.put("item", item);

			// this will further complicate the mathematics of the services
			// ignore this for now
			makeOfferArgs.put("quantity", 1);

			log.info("\nHow much will you offer for this item?:\n");
			Double offer_price = Double.parseDouble(sc.nextLine());
			makeOfferArgs.put("offer_price", offer_price);

			log.info("\nType in the number of payments you will make:\n");
			Integer installments = Integer.parseInt(sc.nextLine());
			makeOfferArgs.put("installments", installments);
			makeOfferArgs.put("user", user);

			Offer offer = cService.makeOffer(makeOfferArgs);
			log.info("\nThe following is the result:\n");
			log.info("		" + offer + "\n");

			log.info("Thanks for your offer!:");
			log.info("		Press 1 to make another offer");
			log.info("		Press anything else to go BACK TO THE STORE");
			String choice = sc.nextLine();
			switch (choice) {
			case "1":
				break;
			default:
				log.info("\nGoing BACK TO THE STORE\n");
				run = false;
				break;
			}
		} while (run);

	}

}
