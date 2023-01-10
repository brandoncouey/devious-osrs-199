package io.ruin.model.inter.teleports

/**
 * @author Jire
 */
object Teleports {

//	const val INTERFACE_ID = 720
//
//	@PublishedApi
//	internal val categories: MutableList<TeleportCategory> = ArrayList()
//
//	@JvmStatic
//	fun Player.open() {
//		loadCategories() // for reloads, disable in production.
//
//		packetSender.run {
//			for (category in categories) {
//				sendString(INTERFACE_ID, category.childID, category.name)
//			}
//		}
//
//		packetSender.setHidden(INTERFACE_ID, 122, true)
//		setTeleportCategory(teleportCategory ?: categories.first())
//
//		sendHomeTeleport()
//
//		previousTeleportDestinations.forEachIndexed { index, dest ->
//			val destChildID = 184 + index
//			val destNameString = dest?.name
//			if (destNameString == null) {
//				packetSender.setHidden(INTERFACE_ID, destChildID, true)
//			} else {
//				packetSender.setHidden(INTERFACE_ID, destChildID, false)
//				packetSender.sendString(
//					INTERFACE_ID, destChildID,
//					destNameString.substring(0, min(15, destNameString.length))
//				)
//			}
//		}
//
//		openInterface(InterfaceType.MAIN, INTERFACE_ID)
//	}
//
//	private fun Player.sendHomeTeleport(
//		string: String = homeTeleportDestination?.name ?: "<col=FF4500>Not selected..."
//	) = packetSender.sendString(INTERFACE_ID, 187, string)
//
//	private fun loadCategories() {
//		categories.clear() // for reloads, disable in production.
//
//		citiesCategory()
//		monstersCategory()
//		bossesCategory()
//		skillingCategory()
//		dungeonsCategory()
//		minigamesCategory()
//		wildernessCategory()
//	}
//
//	private fun Player.setTeleportCategory(category: TeleportCategory) {
//		teleportCategory = category.apply {
//			for (destChildID in DESTINATION_CHILD_RANGE) {
//				val destNameString = childIDToDestination[destChildID]?.name
//				if (destNameString == null) {
//					packetSender.setHidden(INTERFACE_ID, destChildID, true)
//				} else {
//					packetSender.setHidden(INTERFACE_ID, destChildID, false)
//					packetSender.sendString(INTERFACE_ID, destChildID, destNameString)
//				}
//			}
//		}
//	}
//
//	inline operator fun String.invoke(childID: Int, build: TeleportCategoryBuilder.() -> Unit) {
//		categories.add(TeleportCategoryBuilder(childID, this).apply(build))
//	}
//
//	internal val DESTINATION_CHILD_RANGE = 126..171
//
//	private const val NPC_ID = NpcID.SEDRIDOR
//
//	init {
//		loadCategories()
//
//		NPCAction.register(NPC_ID, "talk-to") { p, n ->
//			p.dialogue(
//				NPCDialogue(
//					n,
//					"Hello ${p.name}, I've been working on an all-new teleportation method that will let you " +
//							"quickly access useful places around ${World.type.worldName}. Care to take a look?",
//				).animate(588),
//				OptionsDialogue(
//					Option("Yes, that sounds exciting!", Runnable {
//						p.dialogue(PlayerDialogue("Yes, that sounds exciting! I've always wanted to be experimented on."),
//							ActionDialogue { p.open() })
//					}),
//					Option("No thanks.", Runnable {
//						p.dialogue(
//							PlayerDialogue("No thanks, I'm not too sure about that whole teleportation thing anyways."),
//							NPCDialogue(
//								n,
//								"No worries, it sure is quite experimental stuff! Lots of stuff to worry about, lots of things that can go wro-"
//							),
//							PlayerDialogue("Enough. I'll see you around someday.")
//						)
//					})
//				)
//			)
//		}
//		NPCAction.register(NPC_ID, "teleport") { p, _ -> p.open() }
//
//		InterfaceHandler.register(INTERFACE_ID) { h ->
//			for (category in categories) {
//				h.actions[category.childID] = SimpleAction {
//					it.setTeleportCategory(category)
//				}
//			}
//
//			for (childID in DESTINATION_CHILD_RANGE) {
//				h.actions[childID] = SimpleAction {
//					val category = it.teleportCategory ?: categories.first()
//					val destination = category.childIDToDestination[childID] ?: return@SimpleAction
//
//					if (it.selectingHomeTeleportDestination) {
//						it.selectingHomeTeleportDestination = false
//
//						it.homeTeleportDestination = destination
//						it.sendHomeTeleport()
//						it.dialogue(
//							false,
//							NPCDialogue(
//								NPC_ID,
//								"Your home destination is now set to <col=FF0000>${destination.name}</col>"
//							)
//						)
//					} else {
//						it.previousTeleportDestinations[1] = it.previousTeleportDestinations[0]
//						it.previousTeleportDestinations[0] = destination
//
//						teleports.teleport(it, destination.position)
//					}
//				}
//			}
//
//			/* Previous */
//			h.actions[184] = SimpleAction {
//				teleports.teleport(it, it.previousTeleportDestinations[0]?.position ?: return@SimpleAction)
//			}
//			h.actions[185] = SimpleAction {
//				teleports.teleport(it, it.previousTeleportDestinations[1]?.position ?: return@SimpleAction)
//			}
//
//			/* Home */
//			h.actions[187] = SimpleAction {
//				it.selectingHomeTeleportDestination = true
//				it.sendHomeTeleport("<col=FF4500>Click to select...")
//				it.dialogue(
//					false,
//					NPCDialogue(
//						NPC_ID,
//						"Now click on the destination that you want to set your home to."
//					)
//				)
//			}
//
//			h.closedAction = BiConsumer { p, _ -> p.selectingHomeTeleportDestination = false }
//		}
//	}

}