[
    new MagicAtYourUpkeepTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPlayer upkeepPlayer) {
            game.doAction(new ChangeCountersAction(
                permanent,
                MagicCounterType.Age,
                1
            ));
            return new MagicEvent(
                permanent,
                new MagicMayChoice("Pay cumulative upkeep?"),
                this,
                "PN may\$ lose 2 life for each Age counter on SN. " +
                "If he or she doesn't, sacrifice SN."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            if (event.isYes()) {
                game.doAction(new ChangeLifeAction(event.getPlayer(),-2 * event.getPermanent().getCounters(MagicCounterType.Age)));
            } else {
                game.doAction(new SacrificeAction(event.getPermanent()));
            }
        }
    },
    new MagicPreventDamageTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicDamage damage) {
            if (permanent.isController(damage.getTarget())) {
                damage.prevent();
            }
            return MagicEvent.NONE;
        }
    }
]
