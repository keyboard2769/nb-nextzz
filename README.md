Z for ZEUS but not ZETA
===
but hey, dose it sounds stupid if you read it DOUBLE-ZEUS ??

---
## we are STILL in dev

- cast in the name of test
- ya not guilty
- i am not either
- shame on the guy who polluted my insanity
- hooray i just killed the guy who tried to suffocate us with deadline

---
## todo list

#### issue

- [ ] $ holding mixer gate screws log and weight control up
- [ ] $ over sized stuff should get located near the sixth bin

#### wish

- [ ] $ let feeder ratio adjust based on tph value
- [ ] $ maybe we need to let the feeder speed box send manual pulse
- [ ] $ maybe feeder flux have to be a 'double-linear' model by its evil naturally ?
        .. say, you've got a model composed as (100rpm, 10tph, 900rpm, 60tph)
        .. speed under 100rpm have to compromise a model
        .. that composed as (0rpm, 0tph, 100rpm, 10tph), which is a different model

#### refine

- [ ] $ for translation, we should eliminate any in-loop 'tr' usage
- [ ] $ and maybe we should re-implement the dry-wet control with a stepper
        .. to eliminate all those flags? .. after you made the file system done
- [ ] $ for weigh control manager, may every matt tagged stuff get merged
        .. i mean , get merged into a 'controller' class or something

#### plan

- [ ] $ tre
- [ ] $ dos
- [ ] $ uno
- [ ] $ general import and export functionality
- [ ] $ a linear pane with blocks of 'zero' and 'span' button
- [ ] $ a working manual weighing simulation
- [ ] $ the gate simulation and the case of cut point handling

#### heading

- [ ] $ fill all possible motor load factor : 
        .. SubWeighingTask   : nofound
        .. SubFeederTask     : nofound
        .. SubVProvisionTask : heaing
        .. SubVCombusTask    : await
        .. SubErrorTask      : await

#### last

- [x] $ added current slot overwhelming setting
- [x] $ made the recipe pane work .. forget about the file ex/import stuff
- [x] $ yet another error message and list handling mechanism is now available
- [x] $ added a window listener for the closing operation integrity issue
- [x] $ remade the pid controller
- [x] $ remade the case test of pid controller to have target manipulation
- [x] $ combust settings may works now more than likely
- [x] $ current slot overwhelming coloring

<hr><!--EOF-->
