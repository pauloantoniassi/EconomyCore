package net.tnemc.core.module.cache;

import net.tnemc.core.TNECore;
import net.tnemc.core.compatibility.scheduler.ChoreExecution;
import net.tnemc.core.compatibility.scheduler.ChoreTime;
import net.tnemc.core.module.ModuleUpdateChecker;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/*
 * The New Economy
 * Copyright (C) 2022 - 2023 Daniel "creatorfromhell" Vidmar
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class ModuleFileCache {

  Map<String, List<ModuleFile>> moduleFiles = new HashMap<>();

  public ModuleFileCache() {
    //populate the default TNE modules.
    TNECore.server().scheduler().createDelayedTask(()->populateModules("https://tnemc.net/files/module-version.xml"),
                                                   new ChoreTime(0), ChoreExecution.SECONDARY);
  }

  public Optional<ModuleFile> getModule(String url, String module) {
    for(ModuleFile file : moduleFiles.getOrDefault(url, new ArrayList<>())) {
      if(file.getName().equalsIgnoreCase(module)) return Optional.of(file);
    }
    return Optional.empty();
  }

  public List<ModuleFile> getModules(String url) {
    if(!moduleFiles.containsKey(url)) {
      populateModules(url);
    }

    return moduleFiles.getOrDefault(url, new ArrayList<>());
  }

  private void populateModules(String url) {

    List<ModuleFile> modulesList = new ArrayList<>();

    Optional<Document> document = ModuleUpdateChecker.readUpdateURL(url);
    if(document.isPresent()) {
      Document doc = document.get();

      NodeList mainNodes = doc.getElementsByTagName("modules");
      if(mainNodes != null && mainNodes.getLength() > 0) {
        Node modulesNode = mainNodes.item(0);
        Element element = (Element)modulesNode;

        NodeList modules = element.getElementsByTagName("module");

        for(int i = 0; i < modules.getLength(); i++) {
          Node moduleNode = modules.item(i);

          if(moduleNode.hasAttributes()) {

            Node nameNode = moduleNode.getAttributes().getNamedItem("name");
            if (nameNode != null) {

              Node releasedNode = moduleNode.getAttributes().getNamedItem("released");
              if (releasedNode != null) {
                if(releasedNode.getTextContent().equalsIgnoreCase("yes")) {

                  //We have the correct name, and this module is released.
                  Element moduleElement = (Element)moduleNode;

                  NodeList versions = moduleElement.getElementsByTagName("versions");

                  if(versions != null && versions.getLength() > 0) {

                    NodeList versionsNodes = moduleElement.getElementsByTagName("version");

                    for(int v = 0; v < versionsNodes.getLength(); v++) {

                      Node versionNode = versionsNodes.item(v);
                      if(versionNode != null && versionNode.hasAttributes()) {

                        Element versionElement = (Element)versionNode;

                        Node latest = versionNode.getAttributes().getNamedItem("latest");
                        Node versionReleased = versionNode.getAttributes().getNamedItem("released");

                        if(latest != null && latest.getTextContent().equalsIgnoreCase("yes") &&
                            versionReleased != null && versionReleased.getTextContent().equalsIgnoreCase("yes")) {

                          //We have the latest module version
                          NodeList name = versionElement.getElementsByTagName("name");
                          NodeList jar = versionElement.getElementsByTagName("jar");

                          String current = "";
                          String jarURL = "";

                          if(name != null && name.getLength() > 0) {
                            current = name.item(0).getTextContent();
                          }

                          if(jar != null && jar.getLength() > 0) {
                            jarURL = jar.item(0).getTextContent();
                          }
                          modulesList.add(new ModuleFile(nameNode.getTextContent(), current, jarURL));
                        }
                      }
                    }

                  }

                }

              }
            }
          }
        }
      }
    }

    moduleFiles.put(url, modulesList);
  }
}