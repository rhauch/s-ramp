/*
 * Copyright 2012 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.overlord.sramp.shell.commands.archive;

import javax.xml.namespace.QName;

import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.BaseArtifactType;
import org.overlord.sramp.atom.archive.SrampArchive;
import org.overlord.sramp.atom.archive.SrampArchiveEntry;
import org.overlord.sramp.common.visitors.ArtifactVisitorHelper;
import org.overlord.sramp.shell.api.AbstractShellCommand;
import org.overlord.sramp.shell.util.PrintArtifactMetaDataVisitor;

/**
 * Removes an entry from the current S-RAMP batch archive.
 *
 * @author eric.wittmann@redhat.com
 */
public class ListEntryArchiveCommand extends AbstractShellCommand {

	/**
	 * Constructor.
	 */
	public ListEntryArchiveCommand() {
	}

	/**
	 * @see org.overlord.sramp.shell.api.shell.ShellCommand#printUsage()
	 */
	@Override
	public void printUsage() {
		print("archive:listEntry <archivePath>");
	}

	/**
	 * @see org.overlord.sramp.shell.api.shell.ShellCommand#printHelp()
	 */
	@Override
	public void printHelp() {
		print("The 'listEntry' command is used to display information about");
		print("a single entry in the currently open S-RAMP archive.  The");
		print("path within the archive must be specified.");
	}

	/**
	 * @see org.overlord.sramp.shell.api.shell.ShellCommand#execute()
	 */
	@Override
	public void execute() throws Exception {
		String archivePathArg = requiredArgument(0, "Please include an entry path (relative archive path).");

		QName varName = new QName("archive", "active-archive");
		SrampArchive archive = (SrampArchive) getContext().getVariable(varName);

		if (archive == null) {
			print("No S-RAMP archive is currently open.");
		} else {
			SrampArchiveEntry entry = archive.getEntry(archivePathArg);
			BaseArtifactType metaData = entry.getMetaData();
			print("Entry: " + archivePathArg);
			print("-----");
			PrintArtifactMetaDataVisitor visitor = new PrintArtifactMetaDataVisitor();
			ArtifactVisitorHelper.visitArtifact(visitor, metaData);
		}
	}

}
