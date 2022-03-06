// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.Entity;
import lotr.common.util.LOTRLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class LOTRPacketHandler
{
    public static final SimpleNetworkWrapper networkWrapper;
    
    public LOTRPacketHandler() {
        int id = 0;
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketLogin.Handler.class, (Class)LOTRPacketLogin.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketLoginPlayerData.Handler.class, (Class)LOTRPacketLoginPlayerData.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketPortalPos.Handler.class, (Class)LOTRPacketPortalPos.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketUpdateViewingFaction.Handler.class, (Class)LOTRPacketUpdateViewingFaction.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketAlignment.Handler.class, (Class)LOTRPacketAlignment.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketAlignmentBonus.Handler.class, (Class)LOTRPacketAlignmentBonus.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketAlignDrain.Handler.class, (Class)LOTRPacketAlignDrain.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketAchievement.Handler.class, (Class)LOTRPacketAchievement.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketAchievementRemove.Handler.class, (Class)LOTRPacketAchievementRemove.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketWeaponFX.Handler.class, (Class)LOTRPacketWeaponFX.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketBlockFX.Handler.class, (Class)LOTRPacketBlockFX.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketStopItemUse.Handler.class, (Class)LOTRPacketStopItemUse.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketTraderInfo.Handler.class, (Class)LOTRPacketTraderInfo.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketShield.Handler.class, (Class)LOTRPacketShield.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketOptions.Handler.class, (Class)LOTRPacketOptions.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketMessage.Handler.class, (Class)LOTRPacketMessage.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketHiredInfo.Handler.class, (Class)LOTRPacketHiredInfo.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketHiredGui.Handler.class, (Class)LOTRPacketHiredGui.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketIsOpResponse.Handler.class, (Class)LOTRPacketIsOpResponse.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketFTCooldown.Handler.class, (Class)LOTRPacketFTCooldown.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketWaypointRegion.Handler.class, (Class)LOTRPacketWaypointRegion.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketFTTimer.Handler.class, (Class)LOTRPacketFTTimer.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketFTScreen.Handler.class, (Class)LOTRPacketFTScreen.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketFTBounceClient.Handler.class, (Class)LOTRPacketFTBounceClient.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketWaypointCooldownFraction.Handler.class, (Class)LOTRPacketWaypointCooldownFraction.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketEnvironmentOverlay.Handler.class, (Class)LOTRPacketEnvironmentOverlay.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketUpdatePlayerLocations.Handler.class, (Class)LOTRPacketUpdatePlayerLocations.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketCreateCWPClient.Handler.class, (Class)LOTRPacketCreateCWPClient.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketDeleteCWPClient.Handler.class, (Class)LOTRPacketDeleteCWPClient.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketRenameCWPClient.Handler.class, (Class)LOTRPacketRenameCWPClient.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketCWPProtectionMessage.Handler.class, (Class)LOTRPacketCWPProtectionMessage.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketNPCFX.Handler.class, (Class)LOTRPacketNPCFX.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketBannerData.Handler.class, (Class)LOTRPacketBannerData.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketBannerInvalidName.Handler.class, (Class)LOTRPacketBannerInvalidName.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketFamilyInfo.Handler.class, (Class)LOTRPacketFamilyInfo.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketEntityUUID.Handler.class, (Class)LOTRPacketEntityUUID.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketNPCSpeech.Handler.class, (Class)LOTRPacketNPCSpeech.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketNPCCombatStance.Handler.class, (Class)LOTRPacketNPCCombatStance.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketMiniquest.Handler.class, (Class)LOTRPacketMiniquest.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketMiniquestOffer.Handler.class, (Class)LOTRPacketMiniquestOffer.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketMiniquestRemove.Handler.class, (Class)LOTRPacketMiniquestRemove.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketDate.Handler.class, (Class)LOTRPacketDate.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketTitle.Handler.class, (Class)LOTRPacketTitle.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketUtumnoReturn.Handler.class, (Class)LOTRPacketUtumnoReturn.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketFactionData.Handler.class, (Class)LOTRPacketFactionData.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketBiomeVariantsWatch.Handler.class, (Class)LOTRPacketBiomeVariantsWatch.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketBiomeVariantsUnwatch.Handler.class, (Class)LOTRPacketBiomeVariantsUnwatch.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketMallornEntHeal.Handler.class, (Class)LOTRPacketMallornEntHeal.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketMallornEntSummon.Handler.class, (Class)LOTRPacketMallornEntSummon.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketUtumnoKill.Handler.class, (Class)LOTRPacketUtumnoKill.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketNPCRespawner.Handler.class, (Class)LOTRPacketNPCRespawner.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketNPCIsEating.Handler.class, (Class)LOTRPacketNPCIsEating.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketNPCIsOfferingQuest.Handler.class, (Class)LOTRPacketNPCIsOfferingQuest.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketMiniquestTrackClient.Handler.class, (Class)LOTRPacketMiniquestTrackClient.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketOpenSignEditor.Handler.class, (Class)LOTRPacketOpenSignEditor.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketAlignmentSee.Handler.class, (Class)LOTRPacketAlignmentSee.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketFellowship.Handler.class, (Class)LOTRPacketFellowship.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketFellowshipRemove.Handler.class, (Class)LOTRPacketFellowshipRemove.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketFellowshipNotification.Handler.class, (Class)LOTRPacketFellowshipNotification.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketShareCWPClient.Handler.class, (Class)LOTRPacketShareCWPClient.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketCWPSharedUnlockClient.Handler.class, (Class)LOTRPacketCWPSharedUnlockClient.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketCWPSharedHideClient.Handler.class, (Class)LOTRPacketCWPSharedHideClient.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketEnableAlignmentZones.Handler.class, (Class)LOTRPacketEnableAlignmentZones.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketCancelItemHighlight.Handler.class, (Class)LOTRPacketCancelItemHighlight.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketSetPlayerRotation.Handler.class, (Class)LOTRPacketSetPlayerRotation.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketPledge.Handler.class, (Class)LOTRPacketPledge.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketBrokenPledge.Handler.class, (Class)LOTRPacketBrokenPledge.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketFactionRelations.Handler.class, (Class)LOTRPacketFactionRelations.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketConquestGrid.Handler.class, (Class)LOTRPacketConquestGrid.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketConquestNotification.Handler.class, (Class)LOTRPacketConquestNotification.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketAlignmentChoiceOffer.Handler.class, (Class)LOTRPacketAlignmentChoiceOffer.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketMountControlServerEnforce.Handler.class, (Class)LOTRPacketMountControlServerEnforce.class, id++, Side.CLIENT);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketSell.Handler.class, (Class)LOTRPacketSell.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketMobSpawner.Handler.class, (Class)LOTRPacketMobSpawner.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketBuyUnit.Handler.class, (Class)LOTRPacketBuyUnit.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketHornSelect.Handler.class, (Class)LOTRPacketHornSelect.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketBrewingButton.Handler.class, (Class)LOTRPacketBrewingButton.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketSelectShield.Handler.class, (Class)LOTRPacketSelectShield.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketTraderInteract.Handler.class, (Class)LOTRPacketTraderInteract.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketUnitTraderInteract.Handler.class, (Class)LOTRPacketUnitTraderInteract.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketHiredUnitInteract.Handler.class, (Class)LOTRPacketHiredUnitInteract.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketSetOption.Handler.class, (Class)LOTRPacketSetOption.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketRenamePouch.Handler.class, (Class)LOTRPacketRenamePouch.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketHiredUnitCommand.Handler.class, (Class)LOTRPacketHiredUnitCommand.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketMapTp.Handler.class, (Class)LOTRPacketMapTp.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketIsOpRequest.Handler.class, (Class)LOTRPacketIsOpRequest.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketFastTravel.Handler.class, (Class)LOTRPacketFastTravel.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketFTBounceServer.Handler.class, (Class)LOTRPacketFTBounceServer.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketHiredUnitDismiss.Handler.class, (Class)LOTRPacketHiredUnitDismiss.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketClientInfo.Handler.class, (Class)LOTRPacketClientInfo.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketCreateCWP.Handler.class, (Class)LOTRPacketCreateCWP.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketDeleteCWP.Handler.class, (Class)LOTRPacketDeleteCWP.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketRenameCWP.Handler.class, (Class)LOTRPacketRenameCWP.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketMountOpenInv.Handler.class, (Class)LOTRPacketMountOpenInv.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketEditBanner.Handler.class, (Class)LOTRPacketEditBanner.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketBannerRequestInvalidName.Handler.class, (Class)LOTRPacketBannerRequestInvalidName.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketMiniquestOfferClose.Handler.class, (Class)LOTRPacketMiniquestOfferClose.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketDeleteMiniquest.Handler.class, (Class)LOTRPacketDeleteMiniquest.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketSelectTitle.Handler.class, (Class)LOTRPacketSelectTitle.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketNPCSquadron.Handler.class, (Class)LOTRPacketNPCSquadron.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketItemSquadron.Handler.class, (Class)LOTRPacketItemSquadron.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketEditNPCRespawner.Handler.class, (Class)LOTRPacketEditNPCRespawner.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketCoinExchange.Handler.class, (Class)LOTRPacketCoinExchange.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketMiniquestTrack.Handler.class, (Class)LOTRPacketMiniquestTrack.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketEditSign.Handler.class, (Class)LOTRPacketEditSign.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketSealCracker.Handler.class, (Class)LOTRPacketSealCracker.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketFellowshipCreate.Handler.class, (Class)LOTRPacketFellowshipCreate.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketFellowshipDoPlayer.Handler.class, (Class)LOTRPacketFellowshipDoPlayer.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketFellowshipDisband.Handler.class, (Class)LOTRPacketFellowshipDisband.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketFellowshipRename.Handler.class, (Class)LOTRPacketFellowshipRename.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketFellowshipLeave.Handler.class, (Class)LOTRPacketFellowshipLeave.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketFellowshipSetIcon.Handler.class, (Class)LOTRPacketFellowshipSetIcon.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketFellowshipRespondInvite.Handler.class, (Class)LOTRPacketFellowshipRespondInvite.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketFellowshipToggle.Handler.class, (Class)LOTRPacketFellowshipToggle.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketShareCWP.Handler.class, (Class)LOTRPacketShareCWP.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketCWPSharedHide.Handler.class, (Class)LOTRPacketCWPSharedHide.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketBeaconEdit.Handler.class, (Class)LOTRPacketBeaconEdit.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketAnvilRename.Handler.class, (Class)LOTRPacketAnvilRename.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketAnvilReforge.Handler.class, (Class)LOTRPacketAnvilReforge.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketMercenaryInteract.Handler.class, (Class)LOTRPacketMercenaryInteract.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketPledgeSet.Handler.class, (Class)LOTRPacketPledgeSet.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketClientMQEvent.Handler.class, (Class)LOTRPacketClientMQEvent.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketConquestGridRequest.Handler.class, (Class)LOTRPacketConquestGridRequest.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketAlignmentChoices.Handler.class, (Class)LOTRPacketAlignmentChoices.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketBrandingIron.Handler.class, (Class)LOTRPacketBrandingIron.class, id++, Side.SERVER);
        LOTRPacketHandler.networkWrapper.registerMessage((Class)LOTRPacketMountControl.Handler.class, (Class)LOTRPacketMountControl.class, id++, Side.SERVER);
        LOTRLog.logger.info("LOTR: Registered " + id + " packet types");
    }
    
    public static NetworkRegistry.TargetPoint nearEntity(final Entity entity, final double range) {
        return new NetworkRegistry.TargetPoint(entity.dimension, entity.posX, entity.boundingBox.minY, entity.posZ, range);
    }
    
    static {
        networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel("lotr_");
    }
}
